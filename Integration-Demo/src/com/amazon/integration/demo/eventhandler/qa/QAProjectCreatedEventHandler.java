package com.amazon.integration.demo.eventhandler.qa;

import java.util.logging.Logger;
import com.amazon.core.qa.domain.event.ProjectCreatedEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.AddIntegQAProjectCommand;

public class QAProjectCreatedEventHandler extends AbsEventHandler<ProjectCreatedEvent>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    CommandBus demoSystemCommandBus;
    
    public QAProjectCreatedEventHandler(CommandBus demoSystemCommandBus)
    {
        this.demoSystemCommandBus = demoSystemCommandBus;
    }
    
    @Override
    public void handle(ProjectCreatedEvent event) throws EventHandlerException
    {
        try {
            String submitId = demoSystemCommandBus.send(new AddIntegQAProjectCommand(event.getProjectId(), null));
            logger.fine(String.format("command submitted, returned id[%s]", submitId));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
