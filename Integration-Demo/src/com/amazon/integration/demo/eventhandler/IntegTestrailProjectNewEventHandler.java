package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegTestrailProjectInitCommand;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectNewEvent;

public class IntegTestrailProjectNewEventHandler extends AbsEventHandler<IntegTestrailProjectNewEvent>
{
    CommandBus commandBus;
    
    public IntegTestrailProjectNewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegTestrailProjectNewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new IntegTestrailProjectInitCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
