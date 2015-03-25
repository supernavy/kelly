package com.amazon.core.rm.context;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.PMSystemAssembler;
import com.amazon.core.pm.system.SimplePMSystem;
import com.amazon.core.rm.context.impl.BuildContextImpl;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.RMSystemAssembler;
import com.amazon.core.rm.system.SimpleRMSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.infra.system.AppSystemException;

public class BuildContextTest
{
    AppSystem pmSystem;
    AppSystem rmSystem;
    BuildContext context;
    
    @BeforeTest
    public void init() throws AppSystemException 
    {
        pmSystem = new SimplePMSystem("PM System", Layer.Core);
        new PMSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleRMSystem("RM System", Layer.Core, pmSystem);
        new RMSystemAssembler().assemble(rmSystem);
        
        context = new BuildContextImpl(rmSystem);
        
        pmSystem.start();
        rmSystem.start();
    }
    
    @AfterTest
    public void cleanUp() throws AppSystemException
    {
        rmSystem.shutdown();
        pmSystem.shutdown();
    }
    
    @Test
    public void testCreateBuild() throws Exception
    {      
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        Build build = new Build(productEntity, "Cart X");
        
        Entity<Build> buildEntity = context.createBuild(build.getProductInfo().getId(), build.getBuildName(), build.getPatchName());
        
        Assert.assertNotNull(buildEntity.getId());
        Assert.assertEquals(buildEntity.getData().getBuildName(), build.getBuildName());
        Assert.assertEquals(buildEntity.getData().getProductInfo().getData().getName(), productEntity.getData().getName());
    }
}
