package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.integration.demo.command.MyTestrailPlanHandleTestrailPlanCompleteCommand;
import com.amazon.integration.demo.context.MyTestrailContext;

public class MyTestrailPlanHandleTestrailPlanCompleteCommandHandler extends AbsCommandHandler<MyTestrailPlanHandleTestrailPlanCompleteCommand, Void>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanHandleTestrailPlanCompleteCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Void handle(MyTestrailPlanHandleTestrailPlanCompleteCommand command) throws CommandException
    {
        try {
            integTestrailContext.handleTestrailPlanComplete(command.getTestrailPlanId());
            return null;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

