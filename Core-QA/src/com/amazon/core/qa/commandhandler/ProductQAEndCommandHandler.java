package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.ProductQAEndCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class ProductQAEndCommandHandler extends AbsCommandHandler<ProductQAEndCommand, Entity<ProductQA>>
{
    QAContext qaContext;
    
    public ProductQAEndCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<ProductQA> handle(ProductQAEndCommand command) throws CommandException
    {
        try {
            Entity<ProductQA> ret = qaContext.endProductQA(command.getProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
