package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyProductQAPrepareCommand;
import com.amazon.integration.demo.domain.event.MyProductQANewEvent;

public class MyProductQANewEventHandler extends AbsEventHandler<MyProductQANewEvent>
{
    CommandBus commandBus;
    
    public MyProductQANewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyProductQANewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyProductQAPrepareCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
