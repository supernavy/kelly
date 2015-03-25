package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class ProductQANewCommand extends AbsCommand<Entity<ProductQA>>
{
    String productId;

    public ProductQANewCommand(String productId)
    {
        this.productId = productId;
    }
    public String getProductId()
    {
        return productId;
    }
}
