package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailProjectEndCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectEndCommandHandler extends AbsCommandHandler<IntegTestrailProjectEndCommand, Entity<IntegTestrailProject>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailProjectEndCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailProject> handle(IntegTestrailProjectEndCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailProject> ret = integTestrailContext.endIntegTestrailProject(command.getIntegTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

