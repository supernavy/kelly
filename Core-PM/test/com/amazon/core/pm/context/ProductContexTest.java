package com.amazon.core.pm.context;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.context.impl.ProductContextImpl;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.SimplePMSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.infra.system.AppSystemException;

public class ProductContexTest
{
    AppSystem productSystem;
    ProductContext context;
    
    @BeforeTest
    public void init() throws AppSystemException 
    {
        productSystem = new SimplePMSystem("Product System", Layer.Core);
        context = new ProductContextImpl(productSystem);
    }
    
    @AfterTest
    public void cleanUp()
    {
        
    }
    
    @Test
    public void testCreateProduct() throws Exception
    {
        Product p = new Product("Cart","Shopping Cart");
        Entity<Product> entity = context.createProduct(p);
        Assert.assertNotNull(entity.getId());
        Assert.assertEquals(entity.getData().getName(), p.getName());
        Assert.assertEquals(entity.getData().getDesc(), p.getDesc());
    }
    
    @Test
    public void testGetProduct() throws Exception
    {
        Product p = new Product("Cart","Shopping Cart");
        Entity<Product> entity = context.createProduct(p);
        String id = entity.getId();
        Assert.assertNotNull(id);
        
        Entity<Product> loadedEntity = context.loadProduct(id);
        
        Assert.assertEquals(loadedEntity.getData().getName(), p.getName());
        Assert.assertEquals(loadedEntity.getData().getDesc(), p.getDesc());
    }
}
