package com.amazon.core.rm.context.impl;

import java.util.Set;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.domain.event.BuildNewEvent;
import com.amazon.core.rm.system.RMSystem;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;

public class BuildContextImpl extends AbsAppContextImpl implements BuildContext
{
    Repository<Build> buildRepository;
    CommandBus pmSystemCommandBus;

    public BuildContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.buildRepository = appSystem.getRepository(RMSystem.Repository_Build);
        this.pmSystemCommandBus = appSystem.getDependency(RMSystem.System_PM).getCommandBus();
    }

    @Override
    public Entity<Build> createBuild(String pmProductId, String buildName, String patchName) throws AppContextException
    {     
        try {
            Entity<Product> productInfo = pmSystemCommandBus.submit(new GetProductCommand(pmProductId)).getResult();
            if(productInfo==null)
            {
                throw new AppContextException(String.format("no product found in PM system with id[%s]", pmProductId));
            }
            Build build = new Build(productInfo, buildName, patchName);
            Entity<Build> buildEntity = buildRepository.createEntity(build);
            publishEvent(new BuildNewEvent(buildEntity.getId()));
            return buildEntity;
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

    @Override
    public Set<Entity<Build>> findBuild(EntitySpec<Build> spec) throws AppContextException
    {
        try {
            return buildRepository.find(spec);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

}
