package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegBuildQANewCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQANewCommandHandler extends AbsCommandHandler<IntegBuildQANewCommand, Entity<IntegBuildQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegBuildQANewCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegBuildQA> handle(IntegBuildQANewCommand command) throws CommandException
    {
        try {
            Entity<IntegBuildQA> ret = integQAContext.newIntegBuildQA(command.getBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
