package com.amazon.integration.demo.eventhandler.qa;

import java.util.logging.Logger;
import com.amazon.core.qa.domain.event.ProjectPlanAddedEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.AddPlanToIntegQAProjectCommand;

public class QAProjectPlanAddedEventHandler extends AbsEventHandler<ProjectPlanAddedEvent>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    CommandBus demoSystemCommandBus;
    
    public QAProjectPlanAddedEventHandler(CommandBus demoSystemCommandBus)
    {
        this.demoSystemCommandBus = demoSystemCommandBus;
    }
    
    @Override
    public void handle(ProjectPlanAddedEvent event) throws EventHandlerException
    {
        try {
            String submitId = demoSystemCommandBus.send(new AddPlanToIntegQAProjectCommand(event.getProjectId(), event.getPlanName(), null));
            logger.fine(String.format("command submitted, returned id[%s]", submitId));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
