package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegProductQAPrepareCommand;
import com.amazon.integration.demo.domain.event.IntegProductQANewEvent;

public class IntegProductQANewEventHandler extends AbsEventHandler<IntegProductQANewEvent>
{
    CommandBus commandBus;
    
    public IntegProductQANewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegProductQANewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new IntegProductQAPrepareCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
