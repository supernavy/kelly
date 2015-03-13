package com.amazon.infra.eventbus;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.eventbus.EventDistribution;
import com.amazon.infra.eventbus.simple.SimpleEventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.repository.impl.RepositoryMemoryImpl;

public class SimpleEventBusTest
{
    Repository<EventDistribution<?>> r1 = new RepositoryMemoryImpl<EventDistribution<?>>();
    Repository<List<String>> r2 = new RepositoryMemoryImpl<List<String>>();
    EventBus eventBus;
    EventBusTest eventBusTest;
    
    @Before
    public void init() throws RepositoryException 
    {
        eventBus = new SimpleEventBus(r1,r2);
        eventBusTest = new EventBusTest();
    }
    
    @Test
    public void testEvent() throws Exception {
        eventBusTest.testEvent(eventBus);
    }
    
    @Test
    public void testEventHandler() throws Exception {
        eventBusTest.testEventHandler(eventBus);
    }
    
    @Test
    public void testEventHandlerRegisteredAfterEventPublished() throws Exception {
        eventBusTest.testEventHandlerRegisteredAfterEventPublished(eventBus);
    }
    
    @Test
    public void testMultipleEventHandler() throws Exception {
        eventBusTest.testMultipleEventHandler(eventBus);
    }
}
