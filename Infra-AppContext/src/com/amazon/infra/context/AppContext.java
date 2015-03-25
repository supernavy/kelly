package com.amazon.infra.context;

import com.amazon.infra.eventbus.Event;
import com.amazon.infra.system.AppSystem;

public interface AppContext
{
    public void publishEvent(Event event) throws AppContextException;
    public AppSystem getSystem() throws AppContextException;
}
