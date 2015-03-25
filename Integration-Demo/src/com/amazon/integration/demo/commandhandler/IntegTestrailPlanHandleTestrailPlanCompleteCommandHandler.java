package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.integration.demo.command.IntegTestrailPlanHandleTestrailPlanCompleteCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class IntegTestrailPlanHandleTestrailPlanCompleteCommandHandler extends AbsCommandHandler<IntegTestrailPlanHandleTestrailPlanCompleteCommand, Void>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanHandleTestrailPlanCompleteCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Void handle(IntegTestrailPlanHandleTestrailPlanCompleteCommand command) throws CommandException
    {
        try {
            integTestrailContext.handleTestrailPlanComplete(command.getTestrailPlanId());
            return null;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

