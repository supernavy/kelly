package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailPlanNewCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanNewCommandHandler extends AbsCommandHandler<MyTestrailPlanNewCommand, Entity<MyTestrailPlan>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanNewCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailPlan> handle(MyTestrailPlanNewCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailPlan> ret = integTestrailContext.newMyTestrailPlan(command.getMyBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
