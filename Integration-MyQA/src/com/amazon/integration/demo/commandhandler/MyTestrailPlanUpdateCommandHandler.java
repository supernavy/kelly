package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailPlanUpdateCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanUpdateCommandHandler extends AbsCommandHandler<MyTestrailPlanUpdateCommand, Entity<MyTestrailPlan>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanUpdateCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailPlan> handle(MyTestrailPlanUpdateCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailPlan> ret = integTestrailContext.updateMyTestrailPlan(command.getMyTestrailPlanId(), command.getMyTestrailPlan());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

