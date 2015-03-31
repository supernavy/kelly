package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQANewCommand extends AbsCommand<Entity<MyProductQA>>
{
    String productQAId;

    public MyProductQANewCommand(String productQAId)
    {
        this.productQAId = productQAId;
    }
    public String getProductQAId()
    {
        return productQAId;
    }
}
