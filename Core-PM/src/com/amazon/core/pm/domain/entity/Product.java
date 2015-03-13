package com.amazon.core.pm.domain.entity;

public class Product
{
    String name;
    String desc;
    
    public Product(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
