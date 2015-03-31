package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyBuildQAPrepareCommand;
import com.amazon.integration.demo.domain.event.MyBuildQANewEvent;

public class MyBuildQANewEventHandler extends AbsEventHandler<MyBuildQANewEvent>
{
    CommandBus commandBus;
    
    public MyBuildQANewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyBuildQANewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyBuildQAPrepareCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
