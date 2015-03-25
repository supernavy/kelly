package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegBuildQAPrepareCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQAPrepareCommandHandler extends AbsCommandHandler<IntegBuildQAPrepareCommand, Entity<IntegBuildQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegBuildQAPrepareCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegBuildQA> handle(IntegBuildQAPrepareCommand command) throws CommandException
    {
        try {
            Entity<IntegBuildQA> ret = integQAContext.prepareIntegBuildQA(command.getIntegBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
