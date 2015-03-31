package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyTestrailProjectInitCommand;
import com.amazon.integration.demo.domain.event.MyTestrailProjectNewEvent;

public class MyTestrailProjectNewEventHandler extends AbsEventHandler<MyTestrailProjectNewEvent>
{
    CommandBus commandBus;
    
    public MyTestrailProjectNewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyTestrailProjectNewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyTestrailProjectInitCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
