package com.amazon.core.pm.commandhandler;

import java.util.Set;
import com.amazon.core.pm.command.GetAllProductsCommand;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.ProductContextException;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public class GetAllProductsCommandHandler extends AbsCommandHandler<GetAllProductsCommand, Set<Entity<Product>>>
{
    ProductContext productContext;
    public GetAllProductsCommandHandler(ProductContext productContext)
    {
        this.productContext = productContext;
    }

    @Override
    public Set<Entity<Product>> handle(GetAllProductsCommand command) throws CommandException
    {
        try {
            return productContext.find(new EntitySpec<Product>(){

                @Override
                public boolean matches(Entity<Product> entity)
                {
                    return true;
                }});
        } catch (ProductContextException e) {
            throw new CommandException(e);
        }
    }
    
}
