package com.amazon.core.qa.eventhandler.rm;

import com.amazon.core.qa.command.BuildQANewCommand;
import com.amazon.core.rm.domain.event.BuildNewEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;

public class BuildNewEventHandler extends AbsEventHandler<BuildNewEvent>
{
    CommandBus commandBus;
    
    public BuildNewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(BuildNewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new BuildQANewCommand(event.getEntityId()));
        } catch (CommandBusException e) {
            throw new EventHandlerException(e);
        }
    }
}
