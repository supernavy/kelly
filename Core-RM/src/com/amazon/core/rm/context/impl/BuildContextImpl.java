package com.amazon.core.rm.context.impl;

import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class BuildContextImpl extends AbsAppContextImpl implements BuildContext
{
    Repository<Build> buildRepository;
    CommandBus pmSystemCommandBus;

    public BuildContextImpl(EventBus eventBus, Repository<Build> buildRepository, CommandBus pmSystemCommandBus)
    {
        super(eventBus);
        this.buildRepository = buildRepository;
        this.pmSystemCommandBus = pmSystemCommandBus;
    }

    @Override
    public Entity<Build> createBuild(String pmProductId, String buildName, String patchName) throws AppContextException
    {     
        try {
            Entity<Product> productInfo = pmSystemCommandBus.submit(new GetProductCommand(pmProductId)).getResult();
            Build build = new Build(productInfo, buildName, patchName);
            return buildRepository.createEntity(build);
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<Build> loadBuild(String id) throws AppContextException
    {
        try {
            return buildRepository.load(id);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

}
