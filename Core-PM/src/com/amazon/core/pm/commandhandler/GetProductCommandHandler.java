package com.amazon.core.pm.commandhandler;

import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.ProductContextException;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class GetProductCommandHandler extends AbsCommandHandler<GetProductCommand, Entity<Product>>
{
    ProductContext productContext;
    public GetProductCommandHandler(ProductContext productContext)
    {
        this.productContext = productContext;
    }

    @Override
    public Entity<Product> handle(GetProductCommand command) throws CommandException
    {
        try {
            return productContext.loadProduct(command.getProductId());
        } catch (ProductContextException e) {
            throw new CommandException(e);
        }
    }
    
}
