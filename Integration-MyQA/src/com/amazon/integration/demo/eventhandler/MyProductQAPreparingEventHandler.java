package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyTestrailProjectNewCommand;
import com.amazon.integration.demo.domain.event.MyProductQAPreparingEvent;

public class MyProductQAPreparingEventHandler extends AbsEventHandler<MyProductQAPreparingEvent>
{
    CommandBus commandBus;
    
    public MyProductQAPreparingEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyProductQAPreparingEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyTestrailProjectNewCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
