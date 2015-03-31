package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class ProductQAUpdateCommand extends AbsCommand<Entity<ProductQA>>
{
    String productQAId;
    ProductQA productQA;
    public ProductQAUpdateCommand(String productQAId, ProductQA productQA)
    {
        this.productQAId = productQAId;
        this.productQA = productQA;
    }
    public String getProductQAId()
    {
        return productQAId;
    }
    public ProductQA getProductQA()
    {
        return productQA;
    }
}
