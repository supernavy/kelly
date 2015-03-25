package com.amazon.core.pm.domain.entity;

import java.io.Serializable;

public class Product implements Serializable
{
    /**
     * TODO
     */
    private static final long serialVersionUID = -296697034219688389L;
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
