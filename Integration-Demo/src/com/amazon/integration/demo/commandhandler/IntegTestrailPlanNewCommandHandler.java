package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegTestrailPlanNewCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanNewCommandHandler extends AbsCommandHandler<IntegTestrailPlanNewCommand, Entity<IntegTestrailPlan>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanNewCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<IntegTestrailPlan> handle(IntegTestrailPlanNewCommand command) throws CommandException
    {
        try {
            Entity<IntegTestrailPlan> ret = integTestrailContext.newIntegTestrailPlan(command.getIntegBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
