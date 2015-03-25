package com.amazon.infra.context;

import java.util.logging.Logger;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventBusException;
import com.amazon.infra.system.AppSystem;

public class AbsAppContextImpl implements AppContext
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    AppSystem appSystem;
    
    public AbsAppContextImpl(AppSystem appSystem)
    {
        this.appSystem = appSystem;
    }

    @Override
    public void publishEvent(Event event) throws AppContextException
    {
        String eventDistributionId;
        try {
            eventDistributionId = appSystem.getEventBus().publish(event);
            logger.finest(String.format("Context sent event[%s] to eventbus, returned[%s]", event, eventDistributionId));       
        } catch (EventBusException e) {
            throw new AppContextException(e);
        }      
    }

    @Override
    public AppSystem getSystem() throws AppContextException
    {
        return this.appSystem;
    }
}
