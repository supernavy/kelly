package com.amazon.core.pm.command;

import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class CreateProductCommand extends AbsCommand<Entity<Product>>
{
    String name;
    String desc;
    public CreateProductCommand(String name, String desc)
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
}
