package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.IntegBuildQAUpdateTestrailPlanCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQAUpdateTestrailPlanCommandHandler extends AbsCommandHandler<IntegBuildQAUpdateTestrailPlanCommand, Entity<IntegBuildQA>>
{
    IntegQAContext integQAContext;
    
    
    public IntegBuildQAUpdateTestrailPlanCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<IntegBuildQA> handle(IntegBuildQAUpdateTestrailPlanCommand command) throws CommandException
    {
        try {
            Entity<IntegBuildQA> ret = integQAContext.updateIntegBuildQAWithTestrailPlan(command.getIntegBuildQAId(), command.getIntegTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
