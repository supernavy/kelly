package com.amazon.core.pm.command;

import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class GetProductCommand extends AbsCommand<Entity<Product>>
{
    String productId;

    public GetProductCommand(String productId)
    {
        this.productId = productId;
    }

    public String getProductId()
    {
        return productId;
    }
}
