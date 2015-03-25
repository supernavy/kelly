package com.amazon.integration.demo.context.impl;

import java.util.Set;
import com.amazon.core.qa.command.BuildQAGetCommand;
import com.amazon.core.qa.command.ProductQAGetCommand;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;
import com.amazon.integration.demo.domain.entity.IntegProductQA;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;
import com.amazon.integration.demo.domain.event.IntegBuildQAEndEvent;
import com.amazon.integration.demo.domain.event.IntegBuildQANewEvent;
import com.amazon.integration.demo.domain.event.IntegBuildQAPreparingEvent;
import com.amazon.integration.demo.domain.event.IntegBuildQATestingEvent;
import com.amazon.integration.demo.domain.event.IntegProductQAEndEvent;
import com.amazon.integration.demo.domain.event.IntegProductQANewEvent;
import com.amazon.integration.demo.domain.event.IntegProductQAPreparingEvent;
import com.amazon.integration.demo.domain.event.IntegProductQATestingEvent;
import com.amazon.integration.demo.system.DemoSystem;

public class IntegQAContextImpl extends AbsAppContextImpl implements IntegQAContext
{
    Repository<IntegProductQA> integProductQARepo;
    Repository<IntegBuildQA> integBuildQARepo;
    
    CommandBus qaSystemCommandBus;
    CommandBus pmSystemCommandBus;
    CommandBus rmSystemCommandBus;

    IntegTestrailContext integTestrailContext;
    public IntegQAContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.integProductQARepo = appSystem.getRepository(DemoSystem.Repository_IntegProductQA);
        this.integBuildQARepo = appSystem.getRepository(DemoSystem.Repository_IntegBuildQA);
        
        this.qaSystemCommandBus = appSystem.getDependency(DemoSystem.System_QA).getCommandBus();
        this.pmSystemCommandBus = appSystem.getDependency(DemoSystem.System_PM).getCommandBus();
        this.rmSystemCommandBus = appSystem.getDependency(DemoSystem.System_RM).getCommandBus();
    }
    
    public void setIntegTestrailContext(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegProductQA> newIntegProductQA(String productQAId) throws AppContextException
    {
        try {
            Entity<ProductQA> productQAEntity = qaSystemCommandBus.submit(new ProductQAGetCommand(productQAId)).getResult();
            IntegProductQA integProductQA = new IntegProductQA(productQAEntity);
            Entity<IntegProductQA> integProductQAEntity = integProductQARepo.createEntity(integProductQA);
            publishEvent(new IntegProductQANewEvent(integProductQAEntity.getId()));
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegProductQA> prepareIntegProductQA(String integProductQAId) throws AppContextException
    {
        try {
            Entity<IntegProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            IntegProductQA.Status oldStatus = integProductQAEntity.getData().getStatus();
            IntegProductQA integProductQA = integProductQAEntity.getData().prepare();
            IntegProductQA.Status newStatus = integProductQA.getStatus();
            integProductQAEntity = integProductQARepo.updateEntity(integProductQAEntity.getId(), integProductQA);
            publishIntegProductQAEvent(oldStatus, newStatus, integProductQAEntity.getId());
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegProductQA> updateIntegProductQAWithTestrailProjecct(String integProductQAId, String integTestrailProjectId) throws AppContextException
    {
        try {
            Entity<IntegProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            IntegProductQA.Status oldStatus = integProductQAEntity.getData().getStatus();
            Entity<IntegTestrailProject> integTestrailProjectEntity = integTestrailContext.loadIntegTestrailProject(integTestrailProjectId);
            IntegProductQA integProductQA = integProductQAEntity.getData().updateIntegTestrailProjectInfo(integTestrailProjectEntity);
            IntegProductQA.Status newStatus = integProductQA.getStatus();
            integProductQAEntity = integProductQARepo.updateEntity(integProductQAEntity.getId(), integProductQA);
            publishIntegProductQAEvent(oldStatus, newStatus, integProductQAEntity.getId());
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private void publishIntegProductQAEvent(IntegProductQA.Status oldStatus, IntegProductQA.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Preparing: publishEvent(new IntegProductQAPreparingEvent(id));
                case Testing: publishEvent(new IntegProductQATestingEvent(id));
                case End: publishEvent(new IntegProductQAEndEvent(id));
                default:
                    break;
            }            
        }
    }

    @Override
    public Entity<IntegProductQA> endIntegProductQA(String integProductQAId) throws AppContextException
    {
        try {
            Entity<IntegProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            IntegProductQA.Status oldStatus = integProductQAEntity.getData().getStatus();
            IntegProductQA integProductQA = integProductQAEntity.getData().end();
            IntegProductQA.Status newStatus = integProductQA.getStatus();
            integProductQAEntity = integProductQARepo.updateEntity(integProductQAEntity.getId(), integProductQA);
            publishIntegProductQAEvent(oldStatus, newStatus, integProductQAEntity.getId());
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegBuildQA> newIntegBuildQA(String buildQAId) throws AppContextException
    {
        try {
            final Entity<BuildQA> buildQAEntity = qaSystemCommandBus.submit(new BuildQAGetCommand(buildQAId)).getResult();
            Set<Entity<IntegProductQA>> integProductQAs = integProductQARepo.find(new EntitySpec<IntegProductQA>()
            {

                @Override
                public boolean matches(Entity<IntegProductQA> entity)
                {
                    if (entity.getData().getProductQAInfo().getId().equals(buildQAEntity.getData().getProductQAInfo().getId())) {
                        return true;
                    }
                    return false;
                }
            });

            Entity<IntegProductQA> integProductQA = integProductQAs.iterator().next();
            IntegBuildQA newIntegBuildQA = new IntegBuildQA(buildQAEntity, integProductQA);
            Entity<IntegBuildQA> integBuildQAEntity = integBuildQARepo.createEntity(newIntegBuildQA);
            publishEvent(new IntegBuildQANewEvent(integBuildQAEntity.getId()));
            return integBuildQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private void publishIntegBuildQAEvent(IntegBuildQA.Status oldStatus, IntegBuildQA.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Preparing: publishEvent(new IntegBuildQAPreparingEvent(id));
                case Testing: publishEvent(new IntegBuildQATestingEvent(id));
                case End: publishEvent(new IntegBuildQAEndEvent(id));
                default:
                    break;
            }
        }
    }

    @Override
    public Entity<IntegBuildQA> prepareIntegBuildQA(String integBuildQAEntityId) throws AppContextException
    {
        try {
            Entity<IntegBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            IntegBuildQA.Status oldStatus = integBuildQAEntity.getData().getStatus();
            IntegBuildQA newIntegBuildQA = integBuildQAEntity.getData().prepare();
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, newIntegBuildQA);
            IntegBuildQA.Status newStatus = integBuildQAEntity.getData().getStatus();
            publishIntegBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegBuildQA> updateIntegBuildQAWithTestrailPlan(String integBuildQAEntityId, String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanInfo = integTestrailContext.loadIntegTestrailPlan(integTestrailPlanId);
            Entity<IntegBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            IntegBuildQA.Status oldStatus = integBuildQAEntity.getData().getStatus();
            IntegBuildQA newIntegBuildQA = integBuildQAEntity.getData().updateTestrailPlanInfo(integTestrailPlanInfo);
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, newIntegBuildQA);
            IntegBuildQA.Status newStatus = integBuildQAEntity.getData().getStatus();
            publishIntegBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegBuildQA> endIntegBuildQA(String integBuildQAEntityId) throws AppContextException
    {
        try {
            Entity<IntegBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            IntegBuildQA.Status oldStatus = integBuildQAEntity.getData().getStatus();
            IntegBuildQA newIntegBuildQA = integBuildQAEntity.getData().end();
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, newIntegBuildQA);
            IntegBuildQA.Status newStatus = integBuildQAEntity.getData().getStatus();
            publishIntegBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegBuildQA> loadIntegBuildQA(String integBuildQAEntityId) throws AppContextException
    {
        try {
            Entity<IntegBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegProductQA> loadIntegProductQA(String integProductQAId) throws AppContextException
    {
        try {
            Entity<IntegProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            
            return integProductQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<IntegProductQA>> findIntegProductQA(EntitySpec<IntegProductQA> spec) throws AppContextException
    {
        try {
            Set<Entity<IntegProductQA>> integProductQAEntities = integProductQARepo.find(spec);
            
            return integProductQAEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<IntegBuildQA>> findIntegBuildQA(EntitySpec<IntegBuildQA> spec) throws AppContextException
    {
        try {
            Set<Entity<IntegBuildQA>> integBuildQAEntities = integBuildQARepo.find(spec);
            
            return integBuildQAEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }
}
