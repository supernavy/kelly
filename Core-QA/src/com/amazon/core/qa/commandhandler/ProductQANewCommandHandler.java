package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.ProductQANewCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class ProductQANewCommandHandler extends AbsCommandHandler<ProductQANewCommand, Entity<ProductQA>>
{
    QAContext qaContext;
    
    public ProductQANewCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<ProductQA> handle(ProductQANewCommand command) throws CommandException
    {
        try {
            Entity<ProductQA> ret = qaContext.newProductQA(command.getProductId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
