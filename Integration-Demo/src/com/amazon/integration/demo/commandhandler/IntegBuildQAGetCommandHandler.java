package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegBuildQAGetCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQAGetCommandHandler extends AbsCommandHandler<IntegBuildQAGetCommand, Entity<IntegBuildQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegBuildQAGetCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegBuildQA> handle(IntegBuildQAGetCommand command) throws CommandException
    {
        try {
            Entity<IntegBuildQA> ret = integQAContext.loadIntegBuildQA(command.getIntegBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
