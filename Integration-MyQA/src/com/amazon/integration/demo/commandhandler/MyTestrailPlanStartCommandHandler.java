package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailPlanStartCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanStartCommandHandler extends AbsCommandHandler<MyTestrailPlanStartCommand, Entity<MyTestrailPlan>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanStartCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailPlan> handle(MyTestrailPlanStartCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailPlan> ret = integTestrailContext.startMyTestrailPlan(command.getMyTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

