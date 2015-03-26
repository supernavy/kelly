package com.amazon.core.qa.context.impl;

import java.util.Set;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.core.qa.domain.entity.BuildQA.Result;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.event.BuildQAEndEvent;
import com.amazon.core.qa.domain.event.BuildQAInProgressEvent;
import com.amazon.core.qa.domain.event.BuildQANewEvent;
import com.amazon.core.qa.domain.event.ProductQAEndEvent;
import com.amazon.core.qa.domain.event.ProductQANewEvent;
import com.amazon.core.qa.domain.event.ProductQAReadyEvent;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.core.qa.system.QASystem;
import com.amazon.core.rm.command.GetBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;

public class QAContextImpl extends AbsAppContextImpl implements QAContext
{
    Repository<ProductQA> productQARepo;
    Repository<BuildQA> buildQARepo;
    CommandBus commandBus;
    CommandBus pmSystemCommandBus;
    CommandBus rmSystemCommandBus;
    
    public QAContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        productQARepo = appSystem.getRepository(QASystem.Repository_ProductQA);
        buildQARepo = appSystem.getRepository(QASystem.Repository_BuildQA);
        
        commandBus = appSystem.getCommandBus();
        pmSystemCommandBus = appSystem.getDependency(QASystem.System_PM).getCommandBus();
        rmSystemCommandBus = appSystem.getDependency(QASystem.System_RM).getCommandBus();
    }


    @Override
    public Entity<ProductQA> newProductQA(String pmProductId) throws AppContextException
    {
        try {
            Entity<Product> productEntity = pmSystemCommandBus.submit(new GetProductCommand(pmProductId)).getResult();
            ProductQA productQA = new ProductQA(productEntity);
            Entity<ProductQA> productQAEntity = productQARepo.createEntity(productQA);
            publishEvent(new ProductQANewEvent(productQAEntity.getId()));
            return productQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    
    @Override
    public Entity<ProductQA> loadProductQA(String productQAId) throws AppContextException
    {
        Entity<ProductQA> ret;
        try {
            ret = productQARepo.load(productQAId);
            return ret;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    private void publishProductQAEvent(ProductQA.Status oldStatus, ProductQA.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Ready: publishEvent(new ProductQAReadyEvent(id)); break;
                case End: publishEvent(new ProductQAEndEvent(id)); break;
                default:
                    break;
            }            
        }
    }
    
    @Override
    public Entity<ProductQA> readyProductQA(String productQAId) throws AppContextException
    {
        try {
            Entity<ProductQA> productQAEntity = productQARepo.load(productQAId);
            ProductQA.Status oldStatus = productQAEntity.getData().getStatus();
            
            ProductQA productQA = productQAEntity.getData().ready();
            
            productQAEntity = productQARepo.updateEntity(productQAEntity.getId(), productQA);
            ProductQA.Status newStatus = productQAEntity.getData().getStatus();
            
            publishProductQAEvent(oldStatus, newStatus, productQAEntity.getId());
            
            return productQAEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<ProductQA> endProductQA(String productQAId) throws AppContextException
    {
        try {
            Entity<ProductQA> productQAEntity = productQARepo.load(productQAId);
            ProductQA.Status oldStatus = productQAEntity.getData().getStatus();
            
            ProductQA productQA = productQAEntity.getData().end();
            
            productQAEntity = productQARepo.updateEntity(productQAEntity.getId(), productQA);
            ProductQA.Status newStatus = productQAEntity.getData().getStatus();
            
            publishProductQAEvent(oldStatus, newStatus, productQAEntity.getId());
            
            return productQAEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public Entity<BuildQA> newBuildQA(String rmBuildId) throws AppContextException
    {
        try {
            final Entity<Build> buildEntity = rmSystemCommandBus.submit(new GetBuildCommand(rmBuildId)).getResult();
            Entity<ProductQA> productQAEntity = productQARepo.find(new EntitySpec<ProductQA>()
            {
                @Override
                public boolean matches(Entity<ProductQA> entity)
                {
                    if(entity.getData().getProductInfo().getId().equals(buildEntity.getData().getProductInfo().getId()))
                    {
                        return true;
                    }
                    return false;
                }
            }).iterator().next();            
            
            BuildQA buildQA = new BuildQA(buildEntity, productQAEntity);
            Entity<BuildQA> buildQAEntity = buildQARepo.createEntity(buildQA);
            publishEvent(new BuildQANewEvent(buildQAEntity.getId()));
            return buildQAEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<BuildQA> loadBuildQA(String buildQAId) throws AppContextException
    {
        Entity<BuildQA> ret;
        try {
            ret = buildQARepo.load(buildQAId);
            return ret;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    private void publishBuildQAEvent(BuildQA.Status oldStatus, BuildQA.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case InProgress: publishEvent(new BuildQAInProgressEvent(id)); break;
                case End: publishEvent(new BuildQAEndEvent(id)); break;
                default:
                    break;
            }            
        }
    }
    
    
    @Override
    public Entity<BuildQA> startBuildQA(String buildQAId) throws AppContextException
    {
        try {
            Entity<BuildQA> buildQAEntity = buildQARepo.load(buildQAId);
            BuildQA.Status oldStatus = buildQAEntity.getData().getStatus();
            
            BuildQA buildQA = buildQAEntity.getData().start();
            
            buildQAEntity = buildQARepo.updateEntity(buildQAEntity.getId(), buildQA);
            BuildQA.Status newStatus = buildQAEntity.getData().getStatus();
            
            publishBuildQAEvent(oldStatus, newStatus, buildQAEntity.getId());
            
            return buildQAEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<BuildQA> endBuildQA(String buildQAId, Result result) throws AppContextException
    {
        try {
            Entity<BuildQA> buildQAEntity = buildQARepo.load(buildQAId);
            BuildQA.Status oldStatus = buildQAEntity.getData().getStatus();
            
            BuildQA buildQA = buildQAEntity.getData().end(result);
            
            buildQAEntity = buildQARepo.updateEntity(buildQAEntity.getId(), buildQA);
            BuildQA.Status newStatus = buildQAEntity.getData().getStatus();
            
            publishBuildQAEvent(oldStatus, newStatus, buildQAEntity.getId());
            
            return buildQAEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }


    @Override
    public Entity<ProductQA> addPlanProductQA(String productQAId, Plan plan) throws AppContextException
    {
        try {
            Entity<ProductQA> productQAEntity = productQARepo.load(productQAId);
            ProductQA.Status oldStatus = productQAEntity.getData().getStatus();
            
            ProductQA productQA = productQAEntity.getData().addPlan(plan);
            
            productQAEntity = productQARepo.updateEntity(productQAEntity.getId(), productQA);
            ProductQA.Status newStatus = productQAEntity.getData().getStatus();
            
            publishProductQAEvent(oldStatus, newStatus, productQAEntity.getId());
            
            return productQAEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }


    @Override
    public Set<Entity<ProductQA>> findProductQA(EntitySpec<ProductQA> spec) throws AppContextException
    {
        try {
            return productQARepo.find(spec);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }


    @Override
    public Set<Entity<BuildQA>> findBuildQA(EntitySpec<BuildQA> spec) throws AppContextException
    {
        try {
            return buildQARepo.find(spec);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }


}
