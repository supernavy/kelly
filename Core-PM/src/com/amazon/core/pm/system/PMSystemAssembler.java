package com.amazon.core.pm.system;

import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.command.DeleteProductCommand;
import com.amazon.core.pm.command.GetAllProductsCommand;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.commandhandler.CreateProductCommandHandler;
import com.amazon.core.pm.commandhandler.DeleteProductCommandHandler;
import com.amazon.core.pm.commandhandler.GetAllProductsCommandHandler;
import com.amazon.core.pm.commandhandler.GetProductCommandHandler;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.impl.ProductContextImpl;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class PMSystemAssembler implements AppSystemAssembler
{
    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {
        //initiate context
        ProductContext pc = new ProductContextImpl(system);
        //create and register command handler
        //create and register event handler
        system.getCommandBus().register(CreateProductCommand.class, new CreateProductCommandHandler(pc));
        system.getCommandBus().register(GetProductCommand.class, new GetProductCommandHandler(pc));
        system.getCommandBus().register(DeleteProductCommand.class, new DeleteProductCommandHandler(pc));
        system.getCommandBus().register(GetAllProductsCommand.class, new GetAllProductsCommandHandler(pc));
    }
}
