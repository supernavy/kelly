package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegProductQAGetCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAGetCommandHandler extends AbsCommandHandler<IntegProductQAGetCommand, Entity<IntegProductQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegProductQAGetCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegProductQA> handle(IntegProductQAGetCommand command) throws CommandException
    {
        try {
            Entity<IntegProductQA> ret = integQAContext.loadIntegProductQA(command.getIntegProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
