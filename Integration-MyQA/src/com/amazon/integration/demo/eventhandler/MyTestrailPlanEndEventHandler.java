package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyBuildQAEndCommand;
import com.amazon.integration.demo.command.MyTestrailPlanGetCommand;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.integration.demo.domain.event.MyTestrailPlanEndEvent;

public class MyTestrailPlanEndEventHandler extends AbsEventHandler<MyTestrailPlanEndEvent>
{
    Logger logger = Logger.getLogger(getClass().getName());
    CommandBus commandBus;
    
    public MyTestrailPlanEndEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(MyTestrailPlanEndEvent event) throws EventHandlerException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = commandBus.submit(new MyTestrailPlanGetCommand(event.getEntityId())).getResult();
            commandBus.submit(new MyBuildQAEndCommand(integTestrailPlanEntity.getData().getMyBuildQAInfo().getId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }

}
