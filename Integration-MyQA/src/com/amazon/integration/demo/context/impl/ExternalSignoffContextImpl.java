package com.amazon.integration.demo.context.impl;

import java.util.Set;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.integration.demo.context.ExternalSignoffContext;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.event.ExternalSignoffAssignedEvent;
import com.amazon.integration.demo.domain.event.ExternalSignoffEndEvent;
import com.amazon.integration.demo.domain.event.ExternalSignoffInProgressEvent;
import com.amazon.integration.demo.domain.event.ExternalSignoffNewEvent;
import com.amazon.integration.demo.system.DemoSystem;

public class ExternalSignoffContextImpl extends AbsAppContextImpl implements ExternalSignoffContext
{
    Repository<ExternalSignoff> externalSignoffRepo;
    MyQAContext integQAContext;
    
    public ExternalSignoffContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.externalSignoffRepo = appSystem.getRepository(DemoSystem.Repository_ExternalSignoff);
    }
    
    public void setMyQAContext(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<ExternalSignoff> newExternalSignoff(String integBuildQAId, String featureName) throws AppContextException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = integQAContext.loadMyBuildQA(integBuildQAId);
            ExternalSignoff externalSignoff = new ExternalSignoff(integBuildQAEntity, featureName);
            Entity<ExternalSignoff> externalSignoffEntity = externalSignoffRepo.createEntity(externalSignoff);
            publishEvent(new ExternalSignoffNewEvent(externalSignoffEntity.getId()));
            return externalSignoffEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    private void publishExternalSignoffEvent(ExternalSignoff.Status oldStatus, ExternalSignoff.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Assigned: publishEvent(new ExternalSignoffAssignedEvent(id)); break;
                case InProgress: publishEvent(new ExternalSignoffInProgressEvent(id)); break;
                case End: publishEvent(new ExternalSignoffEndEvent(id)); break;
                default:
                    break;
            }
        }
    }
    
    @Override
    public Entity<ExternalSignoff> sendRequestExternalSignoff(String externalSignoffId) throws AppContextException
    {
        try {
            Entity<ExternalSignoff> externalSignoffEntity = externalSignoffRepo.load(externalSignoffId);
            ExternalSignoff.Status oldStatus = externalSignoffEntity.getData().getStatus();
            ExternalSignoff externalSignoff = externalSignoffEntity.getData().sendRequest();
            ExternalSignoff.Status newStatus = externalSignoff.getStatus();
            externalSignoffEntity = externalSignoffRepo.updateEntity(externalSignoffEntity.getId(), externalSignoff);
            publishExternalSignoffEvent(oldStatus, newStatus, externalSignoffEntity.getId());
            return externalSignoffEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<ExternalSignoff> assignExternalSignoff(String externalSignoffId, ExternalSignoff.Owner owner) throws AppContextException
    {
        try {
            Entity<ExternalSignoff> externalSignoffEntity = externalSignoffRepo.load(externalSignoffId);
            ExternalSignoff.Status oldStatus = externalSignoffEntity.getData().getStatus();
            ExternalSignoff externalSignoff = externalSignoffEntity.getData().assign(owner);
            ExternalSignoff.Status newStatus = externalSignoff.getStatus();
            externalSignoffEntity = externalSignoffRepo.updateEntity(externalSignoffEntity.getId(), externalSignoff);
            publishExternalSignoffEvent(oldStatus, newStatus, externalSignoffEntity.getId());
            return externalSignoffEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<ExternalSignoff> endExternalSignoff(String externalSignoffId) throws AppContextException
    {
        try {
            Entity<ExternalSignoff> externalSignoffEntity = externalSignoffRepo.load(externalSignoffId);
            ExternalSignoff.Status oldStatus = externalSignoffEntity.getData().getStatus();
            ExternalSignoff externalSignoff = externalSignoffEntity.getData().end();
            ExternalSignoff.Status newStatus = externalSignoff.getStatus();
            externalSignoffEntity = externalSignoffRepo.updateEntity(externalSignoffEntity.getId(), externalSignoff);
            publishExternalSignoffEvent(oldStatus, newStatus, externalSignoffEntity.getId());
            return externalSignoffEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<ExternalSignoff> loadExternalSignoff(String externalSignoffId) throws AppContextException
    {
        try {
            Entity<ExternalSignoff> externalSignoffEntity = externalSignoffRepo.load(externalSignoffId);
            return externalSignoffEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<ExternalSignoff>> findExternalSignoff(EntitySpec<ExternalSignoff> spec) throws AppContextException
    {
        try {
            Set<Entity<ExternalSignoff>> externalSignoffEntities = externalSignoffRepo.find(spec);
            
            return externalSignoffEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }
}
