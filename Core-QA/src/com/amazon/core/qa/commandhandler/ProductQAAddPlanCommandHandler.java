package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.ProductQAAddPlanCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class ProductQAAddPlanCommandHandler extends AbsCommandHandler<ProductQAAddPlanCommand, Entity<ProductQA>>
{
    QAContext qaContext;
    
    public ProductQAAddPlanCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<ProductQA> handle(ProductQAAddPlanCommand command) throws CommandException
    {
        try {
            Entity<ProductQA> ret = qaContext.addPlanProductQA(command.getProductQAId(), command.getPlan());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
