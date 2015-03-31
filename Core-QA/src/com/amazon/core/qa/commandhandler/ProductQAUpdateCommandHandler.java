package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.ProductQAUpdateCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class ProductQAUpdateCommandHandler extends AbsCommandHandler<ProductQAUpdateCommand, Entity<ProductQA>>
{
    QAContext qaContext;
    
    public ProductQAUpdateCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<ProductQA> handle(ProductQAUpdateCommand command) throws CommandException
    {
        try {
            Entity<ProductQA> ret = qaContext.updateProductQA(command.getProductQAId(), command.getProductQA());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
