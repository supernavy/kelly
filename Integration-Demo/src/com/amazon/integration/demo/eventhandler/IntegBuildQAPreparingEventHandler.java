package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegTestrailPlanNewCommand;
import com.amazon.integration.demo.domain.event.IntegBuildQAPreparingEvent;

public class IntegBuildQAPreparingEventHandler extends AbsEventHandler<IntegBuildQAPreparingEvent>
{
    CommandBus commandBus;
    
    public IntegBuildQAPreparingEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegBuildQAPreparingEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new IntegTestrailPlanNewCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
