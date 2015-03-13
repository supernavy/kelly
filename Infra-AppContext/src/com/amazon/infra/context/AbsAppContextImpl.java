package com.amazon.infra.context;

import java.util.logging.Logger;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.eventbus.EventBusException;

public class AbsAppContextImpl implements AppContext
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    EventBus eventBus;
    
    public AbsAppContextImpl(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    @Override
    public void publishEvent(Event event) throws AppContextException
    {
        String eventDistributionId;
        try {
            eventDistributionId = eventBus.publish(event);
            logger.finest(String.format("Context sent event[%s] to eventbus, returned[%s]", event, eventDistributionId));       
        } catch (EventBusException e) {
            throw new AppContextException(e);
        }      
    }
}
