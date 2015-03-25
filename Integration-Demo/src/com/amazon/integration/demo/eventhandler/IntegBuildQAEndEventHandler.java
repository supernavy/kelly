package com.amazon.integration.demo.eventhandler;

import com.amazon.core.qa.command.BuildQAEndCommand;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegBuildQAGetCommand;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;
import com.amazon.integration.demo.domain.event.IntegBuildQAEndEvent;

public class IntegBuildQAEndEventHandler extends AbsEventHandler<IntegBuildQAEndEvent>
{
    CommandBus commandBus;;
    CommandBus qaSystemCommandBus;
    
    public IntegBuildQAEndEventHandler(CommandBus commandBus, CommandBus qaSystemCommandBus)
    {
        this.commandBus = commandBus;
        this.qaSystemCommandBus = qaSystemCommandBus;
    }

    @Override
    public void handle(IntegBuildQAEndEvent event) throws EventHandlerException
    {
        try {
            Entity<IntegBuildQA> integBuildQAEntity = commandBus.submit(new IntegBuildQAGetCommand(event.getEntityId())).getResult();
            
            //TODO need calculate the result but not always pass 
            qaSystemCommandBus.submit(new BuildQAEndCommand(integBuildQAEntity.getData().getBuildQAInfo().getId(), BuildQA.Result.Pass));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
