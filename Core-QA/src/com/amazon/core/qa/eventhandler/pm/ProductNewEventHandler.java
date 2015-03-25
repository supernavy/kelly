package com.amazon.core.qa.eventhandler.pm;

import com.amazon.core.pm.domain.event.ProductNewEvent;
import com.amazon.core.qa.command.ProductQANewCommand;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;

public class ProductNewEventHandler extends AbsEventHandler<ProductNewEvent>
{
    CommandBus commandBus;
    
    public ProductNewEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(ProductNewEvent event) throws EventHandlerException
    {
        try {
            commandBus.submit(new ProductQANewCommand(event.getProductId()));
        } catch (CommandBusException e) {
            throw new EventHandlerException(e);
        }
    }
}
