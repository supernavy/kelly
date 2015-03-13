package com.amazon.core.qa.context.impl;

import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.context.TestCaseContext;
import com.amazon.core.qa.domain.entity.TestCase;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class TestCaseContextImpl extends AbsAppContextImpl implements TestCaseContext
{
    Repository<TestCase> testCaseRepo;
    CommandBus pmSystemCommandBus;
    
    public TestCaseContextImpl(EventBus eventBus, Repository<TestCase> testCaseRepo, CommandBus pmSystemCommandBus)
    {
        super(eventBus);
        this.testCaseRepo = testCaseRepo;
        this.pmSystemCommandBus = pmSystemCommandBus;
    }

    @Override
    public Entity<TestCase> load(String id) throws AppContextException
    {
        try {
            return testCaseRepo.load(id);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }


    @Override
    public Entity<TestCase> createTestCase(String name, String desc, String pmProductId) throws AppContextException
    {  
        try {
            Entity<Product> productInfo = pmSystemCommandBus.submit(new GetProductCommand(pmProductId)).getResult();
            return testCaseRepo.createEntity(new TestCase(name, desc, productInfo));
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

}
