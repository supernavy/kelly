package com.amazon.core.pm.commandhandler;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.ProductSystemAssembler;
import com.amazon.core.pm.system.SimpleProductSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.infra.system.AppSystemException;

public class CommandHandlerTest
{
    AppSystem appSystem;
    
    @BeforeTest
    public void init() throws AppSystemException
    {
        appSystem = new SimpleProductSystem("demo product system", Layer.Core);
        new ProductSystemAssembler().assemble(appSystem);
        appSystem.start();
    }
    
    @AfterTest
    public void cleanUp() throws AppSystemException 
    {
        appSystem.shutdown();
    }
    
    @Test
    public void testCreate() throws Exception {
        CreateProductCommand com = new CreateProductCommand("product1", "first product");
        Entity<Product> entity = appSystem.getCommandBus().submit(com).getResult();
        Assert.assertNotNull(entity);
        Assert.assertNotNull(entity.getId());
    }
    
    @Test
    public void testGet() throws Exception {
        CreateProductCommand com = new CreateProductCommand("product1", "first product");
        Entity<Product> entity = appSystem.getCommandBus().submit(com).getResult();
        Assert.assertNotNull(entity);
        Assert.assertNotNull(entity.getId());
        
        GetProductCommand com1 = new GetProductCommand(entity.getId());
        Entity<Product> entity1 = appSystem.getCommandBus().submit(com1).getResult();
        Assert.assertEquals(entity1.getId(), entity.getId());
        Assert.assertEquals(entity1.getData(), entity.getData());
    }
}
