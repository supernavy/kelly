package com.amazon.core.pm.command;

import com.amazon.infra.commandbus.AbsCommand;

public class DeleteProductCommand extends AbsCommand<Void>
{
    String productId;

    public DeleteProductCommand(String productId)
    {
        this.productId = productId;
    }

    public String getProductId()
    {
        return productId;
    }
}
