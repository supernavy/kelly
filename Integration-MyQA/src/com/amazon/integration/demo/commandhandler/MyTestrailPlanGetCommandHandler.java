package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailPlanGetCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanGetCommandHandler extends AbsCommandHandler<MyTestrailPlanGetCommand, Entity<MyTestrailPlan>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanGetCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailPlan> handle(MyTestrailPlanGetCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailPlan> ret = integTestrailContext.loadMyTestrailPlan(command.getMyTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

