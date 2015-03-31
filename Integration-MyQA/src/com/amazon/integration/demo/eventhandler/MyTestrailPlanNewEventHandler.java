package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyTestrailPlanInitCommand;
import com.amazon.integration.demo.domain.event.MyTestrailPlanNewEvent;

public class MyTestrailPlanNewEventHandler extends AbsEventHandler<MyTestrailPlanNewEvent>
{
    CommandBus commandBus;
    
    public MyTestrailPlanNewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyTestrailPlanNewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyTestrailPlanInitCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
