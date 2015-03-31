package com.amazon.integration.demo.eventhandler.qa;

import com.amazon.core.qa.domain.event.ProductQANewEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyProductQANewCommand;

public class ProductQANewEventHandler extends AbsEventHandler<ProductQANewEvent>
{
    CommandBus commandBus;
    
    public ProductQANewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(ProductQANewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new MyProductQANewCommand(event.getEntityId()));
        } catch (CommandBusException e) {
            throw new EventHandlerException(e);
        }
    }
}
