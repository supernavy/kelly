package com.amazon.core.rm.context;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.ProductSystemAssembler;
import com.amazon.core.pm.system.SimpleProductSystem;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.context.impl.BuildContextImpl;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.ReleaseSystem;
import com.amazon.core.rm.system.ReleaseSystemAssembler;
import com.amazon.core.rm.system.SimpleReleaseSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.repository.Repository;
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
        pmSystem = new SimpleProductSystem("PM System", Layer.Core);
        new ProductSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleReleaseSystem("RM System", Layer.Core, pmSystem);
        new ReleaseSystemAssembler().assemble(rmSystem);
        Repository<Build> buildRepo = rmSystem.getRepository(ReleaseSystem.Repository_Build);
        context = new BuildContextImpl(rmSystem.getEventBus(), buildRepo, rmSystem.getDependency(ReleaseSystem.System_PM).getCommandBus());
        
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
