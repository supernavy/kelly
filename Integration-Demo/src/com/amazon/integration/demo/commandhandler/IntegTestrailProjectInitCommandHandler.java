package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailProjectInitCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectInitCommandHandler extends AbsCommandHandler<IntegTestrailProjectInitCommand, Entity<IntegTestrailProject>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailProjectInitCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailProject> handle(IntegTestrailProjectInitCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailProject> ret = integTestrailContext.initIntegTestrailProject(command.getIntegTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

