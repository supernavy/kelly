package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailProjectNewCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectNewCommandHandler extends AbsCommandHandler<IntegTestrailProjectNewCommand, Entity<IntegTestrailProject>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailProjectNewCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailProject> handle(IntegTestrailProjectNewCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailProject> ret = integTestrailContext.newIntegTestrailProject(command.getIntegProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
