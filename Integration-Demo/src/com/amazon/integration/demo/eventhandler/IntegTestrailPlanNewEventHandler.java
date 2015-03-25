package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegTestrailPlanInitCommand;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanNewEvent;

public class IntegTestrailPlanNewEventHandler extends AbsEventHandler<IntegTestrailPlanNewEvent>
{
    CommandBus commandBus;
    
    public IntegTestrailPlanNewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegTestrailPlanNewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new IntegTestrailPlanInitCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
