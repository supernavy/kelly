package com.amazon.integration.demo.eventhandler;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegTestrailProjectNewCommand;
import com.amazon.integration.demo.domain.event.IntegProductQAPreparingEvent;

public class IntegProductQAPreparingEventHandler extends AbsEventHandler<IntegProductQAPreparingEvent>
{
    CommandBus commandBus;
    
    public IntegProductQAPreparingEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegProductQAPreparingEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new IntegTestrailProjectNewCommand(event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
