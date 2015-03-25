package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailProjectGetCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectGetCommandHandler extends AbsCommandHandler<IntegTestrailProjectGetCommand, Entity<IntegTestrailProject>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailProjectGetCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailProject> handle(IntegTestrailProjectGetCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailProject> ret = integTestrailContext.loadIntegTestrailProject(command.getIntegTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

