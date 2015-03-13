package com.amazon.infra.context;

import com.amazon.infra.eventbus.Event;

public interface AppContext
{
    public void publishEvent(Event event) throws AppContextException;
}
