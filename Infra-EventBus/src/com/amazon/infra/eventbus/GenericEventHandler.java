package com.amazon.infra.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GenericEventHandler<T extends Event> extends AbsEventHandler<T> {

    List<T> history = new ArrayList<T>();
    T event;

    public GenericEventHandler(Class<T> eventType)
    {
        super(eventType);
    }

    @Override
    public void handle(T event) throws EventHandlerException
    {
        synchronized (history) {
            history.add(event);
        }
    }
    
    public T getEvent()
    {
        return getEvent(0);
    }
    
    public synchronized T getEvent(int i)
    {
        if(history.size()>i)
            return history.get(i);
        return null;
    }
    
    public List<T> getHistory()
    {
        return new ArrayList<T>(history);
    }
    
    public T waitUntilInvokedOrTimeout(int index, int amount) throws Exception
    {
        return waitUntilInvokedOrTimeout(index, amount, TimeUnit.SECONDS);
    }
    
    public T waitUntilInvokedOrTimeout(int amount) throws Exception
    {
        return waitUntilInvokedOrTimeout(0, amount, TimeUnit.SECONDS);
    }
    
    public T waitUntilInvokedOrTimeout(int amount, TimeUnit unit) throws Exception
    {
        return waitUntilInvokedOrTimeout(0, amount, unit);
    }
    
    public T waitUntilInvokedOrTimeout(final int i, int amount, TimeUnit unit) throws Exception
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        return es.submit(new Callable<T>()
        {
            @Override
            public T call() throws Exception
            {
                while(getEvent(i) == null)
                {
                    Thread.sleep(100);
                }
                return getEvent(i);
            }
        }).get(amount, unit);
    }
}