package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailPlanInitCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanInitCommandHandler extends AbsCommandHandler<IntegTestrailPlanInitCommand, Entity<IntegTestrailPlan>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanInitCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailPlan> handle(IntegTestrailPlanInitCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailPlan> ret = integTestrailContext.initIntegTestrailPlan(command.getIntegTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

