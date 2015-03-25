package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.IntegTestrailPlanGetAllCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanGetAllCommandHandler extends AbsCommandHandler<IntegTestrailPlanGetAllCommand, Set<Entity<IntegTestrailPlan>>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailPlanGetAllCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Set<Entity<IntegTestrailPlan>> handle(IntegTestrailPlanGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<IntegTestrailPlan>> ret = integTestrailContext.findIntegTestrailPlan(new EntitySpec<IntegTestrailPlan>(){

                @Override
                public boolean matches(Entity<IntegTestrailPlan> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

