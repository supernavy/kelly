package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyTestrailPlanNewCommand;
import com.amazon.integration.demo.domain.event.MyBuildQAPreparingEvent;

public class MyBuildQAPreparingEventHandler extends AbsEventHandler<MyBuildQAPreparingEvent>
{
    CommandBus commandBus;
    
    public MyBuildQAPreparingEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyBuildQAPreparingEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyTestrailPlanNewCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
