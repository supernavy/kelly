package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailPlanGetCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanGetCommandHandler extends AbsCommandHandler<IntegTestrailPlanGetCommand, Entity<IntegTestrailPlan>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanGetCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailPlan> handle(IntegTestrailPlanGetCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailPlan> ret = integTestrailContext.loadIntegTestrailPlan(command.getIntegTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

