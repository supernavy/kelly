package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class ProductQAEndCommand extends AbsCommand<Entity<ProductQA>>
{
    String productQAId;

    public ProductQAEndCommand(String productQAId)
    {
        this.productQAId = productQAId;
    }
    public String getProductQAId()
    {
        return productQAId;
    }
}
