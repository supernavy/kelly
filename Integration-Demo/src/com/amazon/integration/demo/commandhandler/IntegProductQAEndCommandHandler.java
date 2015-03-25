package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegProductQAEndCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAEndCommandHandler extends AbsCommandHandler<IntegProductQAEndCommand, Entity<IntegProductQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegProductQAEndCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegProductQA> handle(IntegProductQAEndCommand command) throws CommandException
    {
        try {
            Entity<IntegProductQA> ret = integQAContext.endIntegProductQA(command.getIntegProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
