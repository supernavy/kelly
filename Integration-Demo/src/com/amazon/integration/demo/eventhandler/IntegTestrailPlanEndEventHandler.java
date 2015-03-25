package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegBuildQAEndCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanGetCommand;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanEndEvent;

public class IntegTestrailPlanEndEventHandler extends AbsEventHandler<IntegTestrailPlanEndEvent>
{
    Logger logger = Logger.getLogger(getClass().getName());
    CommandBus commandBus;
    
    public IntegTestrailPlanEndEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegTestrailPlanEndEvent event) throws EventHandlerException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanEntity = commandBus.submit(new IntegTestrailPlanGetCommand(event.getEntityId())).getResult();
            commandBus.submit(new IntegBuildQAEndCommand(integTestrailPlanEntity.getData().getIntegBuildQAInfo().getId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
