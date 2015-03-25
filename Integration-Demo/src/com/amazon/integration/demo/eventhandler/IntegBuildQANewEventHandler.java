package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegBuildQAPrepareCommand;
import com.amazon.integration.demo.domain.event.IntegBuildQANewEvent;

public class IntegBuildQANewEventHandler extends AbsEventHandler<IntegBuildQANewEvent>
{
    CommandBus commandBus;
    
    public IntegBuildQANewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegBuildQANewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new IntegBuildQAPrepareCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
