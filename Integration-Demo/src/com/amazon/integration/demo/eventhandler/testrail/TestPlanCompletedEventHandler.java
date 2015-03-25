package com.amazon.integration.demo.eventhandler.testrail;

import com.amazon.extension.testrail.domain.event.TestPlanCompletedEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;
import com.amazon.integration.demo.command.IntegTestrailPlanHandleTestrailPlanCompleteCommand;

public class TestPlanCompletedEventHandler extends AbsEventHandler<TestPlanCompletedEvent>
{
    CommandBus commandBus;
    
    public TestPlanCompletedEventHandler(CommandBus commandBus)
    {
        this.commandBus = commandBus;
    }
    
    @Override
    public void handle(TestPlanCompletedEvent event) throws EventHandlerException
    {    
        try {
            commandBus.submit(new IntegTestrailPlanHandleTestrailPlanCompleteCommand(event.getTestPlanId()));
        } catch (CommandBusException e) {
            throw new EventHandlerException(e);
        }
    }

}
