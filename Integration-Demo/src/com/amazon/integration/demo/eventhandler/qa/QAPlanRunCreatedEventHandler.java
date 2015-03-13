package com.amazon.integration.demo.eventhandler.qa;

import java.util.logging.Logger;
import com.amazon.core.qa.domain.event.PlanRunCreatedEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.AddIntegQAPlanRunCommand;

public class QAPlanRunCreatedEventHandler extends AbsEventHandler<PlanRunCreatedEvent>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    CommandBus demoSystemCommandBus;
    
    public QAPlanRunCreatedEventHandler(CommandBus demoSystemCommandBus)
    {
        this.demoSystemCommandBus = demoSystemCommandBus;
    }

    public void handle(PlanRunCreatedEvent event) throws EventHandlerException
    {
        try {
            String submitId = demoSystemCommandBus.send(new AddIntegQAPlanRunCommand(event.getPlanRunId(), null));
            logger.fine(String.format("command submitted, returned id[%s]", submitId));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
