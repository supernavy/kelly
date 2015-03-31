package com.amazon.integration.demo.eventhandler;

import com.amazon.core.qa.command.BuildQAEndCommand;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.MyBuildQAGetCommand;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.event.MyBuildQAEndEvent;

public class MyBuildQAEndEventHandler extends AbsEventHandler<MyBuildQAEndEvent>
{
    CommandBus commandBus;;
    CommandBus qaSystemCommandBus;
    
    public MyBuildQAEndEventHandler(CommandBus commandBus, CommandBus qaSystemCommandBus)
    {
        this.commandBus = commandBus;
        this.qaSystemCommandBus = qaSystemCommandBus;
    }

    @Override
    public void handle(MyBuildQAEndEvent event) throws EventHandlerException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = commandBus.submit(new MyBuildQAGetCommand(event.getEntityId())).getResult();
            
            //TODO need calculate the result but not always pass 
            qaSystemCommandBus.submit(new BuildQAEndCommand(integBuildQAEntity.getData().getBuildQAInfo().getId(), BuildQA.Result.Pass));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
