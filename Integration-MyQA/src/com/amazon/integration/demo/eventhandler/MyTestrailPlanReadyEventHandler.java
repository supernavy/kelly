package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyBuildQAUpdateTestrailPlanCommand;
import com.amazon.integration.demo.command.MyTestrailPlanGetCommand;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.integration.demo.domain.event.MyTestrailPlanReadyEvent;

public class MyTestrailPlanReadyEventHandler extends AbsEventHandler<MyTestrailPlanReadyEvent>
{
    Logger logger = Logger.getLogger(getClass().getName());
    CommandBus commandBus;
    
    public MyTestrailPlanReadyEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyTestrailPlanReadyEvent event) throws EventHandlerException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = commandBus.submit(new MyTestrailPlanGetCommand(event.getEntityId())).getResult();
            commandBus.submit(new MyBuildQAUpdateTestrailPlanCommand(integTestrailPlanEntity.getData().getMyBuildQAInfo().getId(), event.getEntityId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
