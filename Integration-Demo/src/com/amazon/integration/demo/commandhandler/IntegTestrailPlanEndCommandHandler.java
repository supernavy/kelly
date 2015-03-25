package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailPlanEndCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanEndCommandHandler extends AbsCommandHandler<IntegTestrailPlanEndCommand, Entity<IntegTestrailPlan>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanEndCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailPlan> handle(IntegTestrailPlanEndCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailPlan> ret = integTestrailContext.endIntegTestrailPlan(command.getIntegTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

