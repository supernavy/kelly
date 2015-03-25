package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailPlanStartCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanStartCommandHandler extends AbsCommandHandler<IntegTestrailPlanStartCommand, Entity<IntegTestrailPlan>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanStartCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailPlan> handle(IntegTestrailPlanStartCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailPlan> ret = integTestrailContext.startIntegTestrailPlan(command.getIntegTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

