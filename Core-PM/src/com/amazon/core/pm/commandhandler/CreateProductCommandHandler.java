package com.amazon.core.pm.commandhandler;

import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.ProductContextException;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class CreateProductCommandHandler extends AbsCommandHandler<CreateProductCommand, Entity<Product>>
{
    ProductContext productContext;
    public CreateProductCommandHandler(ProductContext productContext)
    {
        this.productContext = productContext;
    }

    @Override
    public Entity<Product> handle(CreateProductCommand command) throws CommandException
    {
        Product p = new Product(command.getName(), command.getDesc());
        Entity<Product> entity;
        try {
            entity = productContext.createProduct(p);
            return entity;
        } catch (ProductContextException e) {
            throw new CommandException(e);
        }        
    }
    
}
