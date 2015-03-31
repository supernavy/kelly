package com.amazon.integration.demo.eventhandler.qa;

import com.amazon.core.qa.domain.event.BuildQANewEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyBuildQANewCommand;

public class BuildQANewEventHandler extends AbsEventHandler<BuildQANewEvent>
{
    CommandBus commandBus;
    
    public BuildQANewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(BuildQANewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyBuildQANewCommand(event.getEntityId()));
        } catch (CommandBusException e) {
            throw new EventHandlerException(e);
        }
    }
}
