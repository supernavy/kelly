package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegBuildQAGetCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanStartCommand;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;
import com.amazon.integration.demo.domain.event.IntegBuildQATestingEvent;

public class IntegBuildQATestingEventHandler extends AbsEventHandler<IntegBuildQATestingEvent>
{
    CommandBus commandBus;
    
    public IntegBuildQATestingEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegBuildQATestingEvent event) throws EventHandlerException
    {
        try {
            Entity<IntegBuildQA> integBuildQAEntity = commandBus.submit(new IntegBuildQAGetCommand(event.getEntityId())).getResult();
            commandBus.submit(new IntegTestrailPlanStartCommand(integBuildQAEntity.getData().getIntegTestrailPlanInfo().getId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
