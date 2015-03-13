package com.amazon.infra.eventbus;

import org.junit.Test;

public class GenericEventHandlerTest
{
    @Test
    public void test() {
        GenericEventHandler<MyEvent> myHandler = new GenericEventHandler<MyEvent>(MyEvent.class);
        System.out.println(myHandler);
    }
    
    public class MyEvent implements Event
    {
        
    }
}
