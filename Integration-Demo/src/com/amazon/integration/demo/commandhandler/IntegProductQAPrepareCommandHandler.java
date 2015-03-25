package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegProductQAPrepareCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAPrepareCommandHandler extends AbsCommandHandler<IntegProductQAPrepareCommand, Entity<IntegProductQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegProductQAPrepareCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegProductQA> handle(IntegProductQAPrepareCommand command) throws CommandException
    {
        try {
            Entity<IntegProductQA> ret = integQAContext.prepareIntegProductQA(command.getIntegProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
