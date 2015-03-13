package com.amazon.core.qa.domain.entity;

import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;

public class TestCase
{
    Entity<Product> productInfo;
    
    String name;
    String desc;
    
    public TestCase(String name, String desc, Entity<Product> productInfo)
    {
        this.name = name;
        this.desc = desc;
        this.productInfo = productInfo;
    } 
    
    public String getName()
    {
        return name;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public Entity<Product> getProjectRef()
    {
        return productInfo;
    }
}
