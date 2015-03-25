package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegBuildQAUpdateTestrailPlanCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanGetCommand;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanReadyEvent;

public class IntegTestrailPlanReadyEventHandler extends AbsEventHandler<IntegTestrailPlanReadyEvent>
{
    Logger logger = Logger.getLogger(getClass().getName());
    CommandBus commandBus;
    
    public IntegTestrailPlanReadyEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegTestrailPlanReadyEvent event) throws EventHandlerException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanEntity = commandBus.submit(new IntegTestrailPlanGetCommand(event.getEntityId())).getResult();
            commandBus.submit(new IntegBuildQAUpdateTestrailPlanCommand(integTestrailPlanEntity.getData().getIntegBuildQAInfo().getId(), event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
