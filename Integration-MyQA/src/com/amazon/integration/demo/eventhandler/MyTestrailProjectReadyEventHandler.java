package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyProductQAUpdateTestrailProjectCommand;
import com.amazon.integration.demo.command.MyTestrailProjectGetCommand;
import com.amazon.integration.demo.domain.entity.MyProductQA;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;
import com.amazon.integration.demo.domain.event.MyTestrailProjectReadyEvent;

public class MyTestrailProjectReadyEventHandler extends AbsEventHandler<MyTestrailProjectReadyEvent>
{
    Logger logger = Logger.getLogger(getClass().getName());
    CommandBus commandBus;
    
    public MyTestrailProjectReadyEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyTestrailProjectReadyEvent event) throws EventHandlerException
    {
        try {
            Entity<MyTestrailProject> integTestrailProjectEntity = commandBus.submit(new MyTestrailProjectGetCommand(event.getEntityId())).getResult();
            Entity<MyProductQA> integProductQAEntity = commandBus.submit(new MyProductQAUpdateTestrailProjectCommand(integTestrailProjectEntity.getData().getMyProductQAInfo().getId(), event.getEntityId())).getResult();
            logger.fine(String.format("updated Entity<MyProductQA>[%s]",integProductQAEntity));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
