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
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.entity.MyProductQA;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;
import com.amazon.integration.demo.domain.event.MyBuildQAEndEvent;
import com.amazon.integration.demo.domain.event.MyBuildQANewEvent;
import com.amazon.integration.demo.domain.event.MyBuildQAPreparingEvent;
import com.amazon.integration.demo.domain.event.MyBuildQATestingEvent;
import com.amazon.integration.demo.domain.event.MyProductQAEndEvent;
import com.amazon.integration.demo.domain.event.MyProductQANewEvent;
import com.amazon.integration.demo.domain.event.MyProductQAPreparingEvent;
import com.amazon.integration.demo.domain.event.MyProductQATestingEvent;
import com.amazon.integration.demo.system.DemoSystem;

public class MyQAContextImpl extends AbsAppContextImpl implements MyQAContext
{
    Repository<MyProductQA> integProductQARepo;
    Repository<MyBuildQA> integBuildQARepo;
    
    CommandBus qaSystemCommandBus;
    CommandBus pmSystemCommandBus;
    CommandBus rmSystemCommandBus;

    MyTestrailContext integTestrailContext;
    public MyQAContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.integProductQARepo = appSystem.getRepository(DemoSystem.Repository_MyProductQA);
        this.integBuildQARepo = appSystem.getRepository(DemoSystem.Repository_MyBuildQA);
        
        this.qaSystemCommandBus = appSystem.getDependency(DemoSystem.System_QA).getCommandBus();
        this.pmSystemCommandBus = appSystem.getDependency(DemoSystem.System_PM).getCommandBus();
        this.rmSystemCommandBus = appSystem.getDependency(DemoSystem.System_RM).getCommandBus();
    }
    
    public void setMyTestrailContext(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyProductQA> newMyProductQA(String productQAId) throws AppContextException
    {
        try {
            Entity<ProductQA> productQAEntity = qaSystemCommandBus.submit(new ProductQAGetCommand(productQAId)).getResult();
            MyProductQA integProductQA = new MyProductQA(productQAEntity);
            Entity<MyProductQA> integProductQAEntity = integProductQARepo.createEntity(integProductQA);
            publishEvent(new MyProductQANewEvent(integProductQAEntity.getId()));
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyProductQA> prepareMyProductQA(String integProductQAId) throws AppContextException
    {
        try {
            Entity<MyProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            MyProductQA.Status oldStatus = integProductQAEntity.getData().getStatus();
            MyProductQA integProductQA = integProductQAEntity.getData().prepare();
            MyProductQA.Status newStatus = integProductQA.getStatus();
            integProductQAEntity = integProductQARepo.updateEntity(integProductQAEntity.getId(), integProductQA);
            publishMyProductQAEvent(oldStatus, newStatus, integProductQAEntity.getId());
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyProductQA> updateMyProductQAWithTestrailProjecct(String integProductQAId, String integTestrailProjectId) throws AppContextException
    {
        try {
            Entity<MyProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            MyProductQA.Status oldStatus = integProductQAEntity.getData().getStatus();
            Entity<MyTestrailProject> integTestrailProjectEntity = integTestrailContext.loadMyTestrailProject(integTestrailProjectId);
            MyProductQA integProductQA = integProductQAEntity.getData().updateMyTestrailProjectInfo(integTestrailProjectEntity);
            MyProductQA.Status newStatus = integProductQA.getStatus();
            integProductQAEntity = integProductQARepo.updateEntity(integProductQAEntity.getId(), integProductQA);
            publishMyProductQAEvent(oldStatus, newStatus, integProductQAEntity.getId());
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private void publishMyProductQAEvent(MyProductQA.Status oldStatus, MyProductQA.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Preparing: publishEvent(new MyProductQAPreparingEvent(id)); break;
                case Testing: publishEvent(new MyProductQATestingEvent(id)); break;
                case End: publishEvent(new MyProductQAEndEvent(id)); break;
                default:
                    break;
            }            
        }
    }

    @Override
    public Entity<MyProductQA> endMyProductQA(String integProductQAId) throws AppContextException
    {
        try {
            Entity<MyProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            MyProductQA.Status oldStatus = integProductQAEntity.getData().getStatus();
            MyProductQA integProductQA = integProductQAEntity.getData().end();
            MyProductQA.Status newStatus = integProductQA.getStatus();
            integProductQAEntity = integProductQARepo.updateEntity(integProductQAEntity.getId(), integProductQA);
            publishMyProductQAEvent(oldStatus, newStatus, integProductQAEntity.getId());
            return integProductQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyBuildQA> newMyBuildQA(String buildQAId) throws AppContextException
    {
        try {
            final Entity<BuildQA> buildQAEntity = qaSystemCommandBus.submit(new BuildQAGetCommand(buildQAId)).getResult();
            Set<Entity<MyProductQA>> integProductQAs = integProductQARepo.find(new EntitySpec<MyProductQA>()
            {

                @Override
                public boolean matches(Entity<MyProductQA> entity)
                {
                    if (entity.getData().getProductQAInfo().getId().equals(buildQAEntity.getData().getProductQAInfo().getId())) {
                        return true;
                    }
                    return false;
                }
            });

            Entity<MyProductQA> integProductQA = integProductQAs.iterator().next();
            MyBuildQA newMyBuildQA = new MyBuildQA(buildQAEntity, integProductQA);
            Entity<MyBuildQA> integBuildQAEntity = integBuildQARepo.createEntity(newMyBuildQA);
            publishEvent(new MyBuildQANewEvent(integBuildQAEntity.getId()));
            return integBuildQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private void publishMyBuildQAEvent(MyBuildQA oldData, MyBuildQA newData, String id) throws AppContextException {
        if(!oldData.equals(newData))
        {
            switch(newData.getStatus())
            {
                case Preparing: publishEvent(new MyBuildQAPreparingEvent(id)); break;
                case Testing: publishEvent(new MyBuildQATestingEvent(id)); break;
                case End: publishEvent(new MyBuildQAEndEvent(id)); break;
                default:
                    break;
            }
        }
    }

    @Override
    public Entity<MyBuildQA> prepareMyBuildQA(String integBuildQAEntityId) throws AppContextException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            MyBuildQA oldStatus = integBuildQAEntity.getData();
            MyBuildQA newMyBuildQA = integBuildQAEntity.getData().prepare();
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, newMyBuildQA);
            MyBuildQA newStatus = integBuildQAEntity.getData();
            publishMyBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyBuildQA> updateMyBuildQAWithTestrailPlan(String integBuildQAEntityId, String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanInfo = integTestrailContext.loadMyTestrailPlan(integTestrailPlanId);
            Entity<MyBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            MyBuildQA oldStatus = integBuildQAEntity.getData();
            MyBuildQA newMyBuildQA = integBuildQAEntity.getData().updateTestrailPlanInfo(integTestrailPlanInfo);
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, newMyBuildQA);
            MyBuildQA newStatus = integBuildQAEntity.getData();
            publishMyBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyBuildQA> endMyBuildQA(String integBuildQAEntityId) throws AppContextException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            MyBuildQA oldStatus = integBuildQAEntity.getData();
            MyBuildQA newMyBuildQA = integBuildQAEntity.getData().end();
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, newMyBuildQA);
            MyBuildQA newStatus = integBuildQAEntity.getData();
            publishMyBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyBuildQA> loadMyBuildQA(String integBuildQAEntityId) throws AppContextException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyProductQA> loadMyProductQA(String integProductQAId) throws AppContextException
    {
        try {
            Entity<MyProductQA> integProductQAEntity = integProductQARepo.load(integProductQAId);
            
            return integProductQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<MyProductQA>> findMyProductQA(EntitySpec<MyProductQA> spec) throws AppContextException
    {
        try {
            Set<Entity<MyProductQA>> integProductQAEntities = integProductQARepo.find(spec);
            
            return integProductQAEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<MyBuildQA>> findMyBuildQA(EntitySpec<MyBuildQA> spec) throws AppContextException
    {
        try {
            Set<Entity<MyBuildQA>> integBuildQAEntities = integBuildQARepo.find(spec);
            
            return integBuildQAEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyBuildQA> updateMyBuildQA(String integBuildQAEntityId, MyBuildQA myBuildQA) throws AppContextException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = integBuildQARepo.load(integBuildQAEntityId);
            MyBuildQA oldStatus = integBuildQAEntity.getData();
            
            integBuildQAEntity = integBuildQARepo.updateEntity(integBuildQAEntityId, myBuildQA);
            MyBuildQA newStatus = integBuildQAEntity.getData();
            publishMyBuildQAEvent(oldStatus, newStatus, integBuildQAEntityId);
            
            return integBuildQAEntity;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }
}
