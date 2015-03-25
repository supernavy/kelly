package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.ProductQAReadyCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class ProductQAReadyCommandHandler extends AbsCommandHandler<ProductQAReadyCommand, Entity<ProductQA>>
{
    QAContext qaContext;
    
    public ProductQAReadyCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<ProductQA> handle(ProductQAReadyCommand command) throws CommandException
    {
        try {
            Entity<ProductQA> ret = qaContext.readyProductQA(command.getProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
