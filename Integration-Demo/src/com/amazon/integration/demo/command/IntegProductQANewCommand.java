package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQANewCommand extends AbsCommand<Entity<IntegProductQA>>
{
    String productQAId;

    public IntegProductQANewCommand(String productQAId)
    {
        this.productQAId = productQAId;
    }
    public String getProductQAId()
    {
        return productQAId;
    }
}
