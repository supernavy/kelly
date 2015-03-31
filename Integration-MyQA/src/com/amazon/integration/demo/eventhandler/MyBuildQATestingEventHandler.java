package com.amazon.integration.demo.eventhandler;

import com.amazon.core.qa.command.BuildQAStartCommand;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.infra.system.AppSystem;
import com.amazon.integration.demo.command.MyBuildQAGetCommand;
import com.amazon.integration.demo.command.MyTestrailPlanStartCommand;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.event.MyBuildQATestingEvent;
import com.amazon.integration.demo.system.DemoSystem;

public class MyBuildQATestingEventHandler extends AbsEventHandler<MyBuildQATestingEvent>
{
    CommandBus commandBus;
    AppSystem system;
    
    public MyBuildQATestingEventHandler(AppSystem system)
    {
        this.commandBus = system.getCommandBus();
        this.system = system;
    }

    @Override
    public void handle(MyBuildQATestingEvent event) throws EventHandlerException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = commandBus.submit(new MyBuildQAGetCommand(event.getEntityId())).getResult();
            commandBus.submit(new MyTestrailPlanStartCommand(integBuildQAEntity.getData().getMyTestrailPlanInfo().getId()));
            system.getDependency(DemoSystem.System_QA).getCommandBus().submit(new BuildQAStartCommand(integBuildQAEntity.getData().getBuildQAInfo().getId()));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
