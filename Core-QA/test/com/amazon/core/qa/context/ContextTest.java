package com.amazon.core.qa.context;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.PMSystemAssembler;
import com.amazon.core.pm.system.SimplePMSystem;
import com.amazon.core.qa.context.impl.QAContextImpl;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.event.BuildQANewEvent;
import com.amazon.core.qa.domain.event.ProductQANewEvent;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.RMSystemAssembler;
import com.amazon.core.rm.system.SimpleRMSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.GenericEventHandler;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;

public class ContextTest
{
    AppSystem pmSystem;
    AppSystem rmSystem;
    AppSystem qaSystem;
    QAContext qaContext;

    @BeforeTest
    public void init() throws Exception
    {
        pmSystem = new SimplePMSystem("PM System", Layer.Core);
        new PMSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleRMSystem("RM System", Layer.Core, pmSystem);
        new RMSystemAssembler().assemble(rmSystem);
        qaSystem = new SimpleQASystem("demo qa", Layer.Core, pmSystem, rmSystem);
        new QASystemAssembler().assemble(qaSystem);
        
        qaContext = new QAContextImpl(qaSystem);
        
        pmSystem.start();
        rmSystem.start();
        qaSystem.start();
    }

    @AfterTest
    public void cleanUp() throws Exception
    {
        qaSystem.shutdown();
        rmSystem.shutdown();
        pmSystem.shutdown();
    }

    @Test
    public void testNewProductQA() throws Exception
    {
        GenericEventHandler<ProductQANewEvent> productQANewEventHandler = new GenericEventHandler<ProductQANewEvent>(ProductQANewEvent.class);
        qaSystem.getEventBus().registerEventHandler(ProductQANewEvent.class, productQANewEventHandler);
        
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();

        Entity<ProductQA> productQAEntity = qaContext.newProductQA(productEntity.getId());
        Assert.assertNotNull(productQAEntity);
        Assert.assertNotNull(productQAEntity.getId());
        Assert.assertNotNull(productQAEntity.getData());
        productQANewEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(productQANewEventHandler.getEvent());
        Assert.assertEquals(productQANewEventHandler.getEvent().getEntityId(), productQAEntity.getId());
    }
    
    @Test
    public void testNewBuildQA() throws Exception
    {
        GenericEventHandler<BuildQANewEvent> buildQANewEventHandler = new GenericEventHandler<BuildQANewEvent>(BuildQANewEvent.class);
        qaSystem.getEventBus().registerEventHandler(BuildQANewEvent.class, buildQANewEventHandler);
        
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        Entity<ProductQA> productQAEntity = qaContext.newProductQA(productEntity.getId());
        Assert.assertNotNull(productQAEntity);
        
        Build build = new Build(productEntity, "Build X");
        Entity<Build> buildEntity = rmSystem.getCommandBus().submit(new CreateBuildCommand(productEntity.getId(), build.getBuildName(), build.getPatchName())).getResult();

        Entity<BuildQA> buildQAEntity = qaContext.newBuildQA(buildEntity.getId());
        Assert.assertNotNull(buildQAEntity);
        Assert.assertNotNull(buildQAEntity.getId());
        Assert.assertNotNull(buildQAEntity.getData());
        buildQANewEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(buildQANewEventHandler.getEvent());
        Assert.assertEquals(buildQANewEventHandler.getEvent().getEntityId(), buildQAEntity.getId());
    }
}
