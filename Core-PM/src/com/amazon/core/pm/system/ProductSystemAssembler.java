package com.amazon.core.pm.system;

import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.commandhandler.CreateProductCommandHandler;
import com.amazon.core.pm.commandhandler.GetProductCommandHandler;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.impl.ProductContextImpl;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class ProductSystemAssembler implements AppSystemAssembler
{
    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {
        Repository<Product> productRepository = system.getRepository(ProductSystem.Repository_Product);
        //initiate context
        ProductContext pc = new ProductContextImpl(system.getEventBus(), productRepository);
        //create and register command handler
        //create and register event handler
        system.getCommandBus().register(CreateProductCommand.class, new CreateProductCommandHandler(pc));
        system.getCommandBus().register(GetProductCommand.class, new GetProductCommandHandler(pc));
    }
}
