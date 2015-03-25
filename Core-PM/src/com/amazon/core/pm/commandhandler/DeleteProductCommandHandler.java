package com.amazon.core.pm.commandhandler;

import com.amazon.core.pm.command.DeleteProductCommand;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.ProductContextException;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;

public class DeleteProductCommandHandler extends AbsCommandHandler<DeleteProductCommand, Void>
{
    ProductContext productContext;
    public DeleteProductCommandHandler(ProductContext productContext)
    {
        this.productContext = productContext;
    }

    @Override
    public Void handle(DeleteProductCommand command) throws CommandException
    {
        try {
            productContext.deleteProduct(command.getProductId());
            return null;
        } catch (ProductContextException e) {
            throw new CommandException(e);
        }
    }   
}
