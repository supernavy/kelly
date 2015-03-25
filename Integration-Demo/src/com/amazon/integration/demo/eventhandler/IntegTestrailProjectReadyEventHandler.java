package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegProductQAUpdateTestrailProjectCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectGetCommand;
import com.amazon.integration.demo.domain.entity.IntegProductQA;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectReadyEvent;

public class IntegTestrailProjectReadyEventHandler extends AbsEventHandler<IntegTestrailProjectReadyEvent>
{
    Logger logger = Logger.getLogger(getClass().getName());
    CommandBus commandBus;
    
    public IntegTestrailProjectReadyEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(IntegTestrailProjectReadyEvent event) throws EventHandlerException
    {
        try {
            Entity<IntegTestrailProject> integTestrailProjectEntity = commandBus.submit(new IntegTestrailProjectGetCommand(event.getEntityId())).getResult();
            Entity<IntegProductQA> integProductQAEntity = commandBus.submit(new IntegProductQAUpdateTestrailProjectCommand(integTestrailProjectEntity.getData().getIntegProductQAInfo().getId(), event.getEntityId())).getResult();
            logger.fine(String.format("updated Entity<IntegProductQA>[%s]",integProductQAEntity));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
