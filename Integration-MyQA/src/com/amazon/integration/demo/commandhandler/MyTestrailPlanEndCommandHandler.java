package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailPlanEndCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanEndCommandHandler extends AbsCommandHandler<MyTestrailPlanEndCommand, Entity<MyTestrailPlan>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanEndCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailPlan> handle(MyTestrailPlanEndCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailPlan> ret = integTestrailContext.endMyTestrailPlan(command.getMyTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

