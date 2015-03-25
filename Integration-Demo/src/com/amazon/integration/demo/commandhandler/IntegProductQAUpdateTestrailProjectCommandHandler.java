package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegProductQAUpdateTestrailProjectCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAUpdateTestrailProjectCommandHandler extends AbsCommandHandler<IntegProductQAUpdateTestrailProjectCommand, Entity<IntegProductQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegProductQAUpdateTestrailProjectCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegProductQA> handle(IntegProductQAUpdateTestrailProjectCommand command) throws CommandException
    {
        try {
            Entity<IntegProductQA> ret = integQAContext.updateIntegProductQAWithTestrailProjecct(command.getIntegProductQAId(), command.getTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
