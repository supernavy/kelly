package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegProductQANewCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQANewCommandHandler extends AbsCommandHandler<IntegProductQANewCommand, Entity<IntegProductQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegProductQANewCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegProductQA> handle(IntegProductQANewCommand command) throws CommandException
    {
        try {
            Entity<IntegProductQA> ret = integQAContext.newIntegProductQA(command.getProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
