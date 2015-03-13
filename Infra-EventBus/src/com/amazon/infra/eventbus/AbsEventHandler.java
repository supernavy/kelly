package com.amazon.infra.eventbus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class AbsEventHandler<T> {
    Class<T> eventType;
    
    @SuppressWarnings("unchecked")
    public AbsEventHandler()
    {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t ;
        eventType = (Class<T>) p.getActualTypeArguments()[0];
    }
    
    public AbsEventHandler(Class<T> eventType)
    {
        this.eventType = eventType;
    }
    
    public abstract void handle(T event) throws EventHandlerException;
	public Class<T> getEventType()
	{
	    return eventType;
	}
}
