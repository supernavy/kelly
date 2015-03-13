package com.amazon.integration.demo.eventhandler;

import java.util.logging.Logger;
import com.amazon.extension.testrail.event.ResultAddedForCaseEvent;
import com.amazon.extension.testrail.event.TestrailEvent;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerException;

public class TestrailEventHandler extends AbsEventHandler<TestrailEvent>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    CommandBus demoSystemCommandBus;
    
    @Override
    public void handle(TestrailEvent event) throws EventHandlerException
    {
        if(event instanceof ResultAddedForCaseEvent)
        {
            handle((ResultAddedForCaseEvent) event);
        }
    }

    public void handle(ResultAddedForCaseEvent event) throws EventHandlerException
    {
        try {
//            String submitId = demoSystemCommandBus.send(new AddTestrailPlanCommand(event.getPlanRunId()));
//            logger.fine(String.format("command submitted, returned id[%s]", submitId));
        } catch (Exception e) {
            throw new EventHandlerException(e);
        }
    }
}
