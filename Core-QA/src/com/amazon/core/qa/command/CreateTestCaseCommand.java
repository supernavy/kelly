package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.TestCase;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class CreateTestCaseCommand extends AbsCommand<Entity<TestCase>>
{
    String productId;
    String name;
    String desc;
    public CreateTestCaseCommand(String productId, String name, String desc)
    {
        this.productId = productId;
        this.name = name;
        this.desc = desc;
    }
    
    public String getProductId()
    {
        return productId;
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
