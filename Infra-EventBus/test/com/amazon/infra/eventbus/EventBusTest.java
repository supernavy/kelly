package com.amazon.infra.eventbus;

import java.util.concurrent.TimeoutException;
import org.junit.Assert;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventBus;

public class EventBusTest
{
    public void testEvent(EventBus bus) throws Exception
    {
        bus.start();

        MyEvent event1 = new MyEvent("A");
        MyEvent2 event2 = new MyEvent2(2);

        bus.publish(event1);
        bus.publish(event2);

        bus.stop();
    }

    public void testEventHandler(EventBus bus) throws Exception
    {
        GenericEventHandler<MyEvent> eventHandler = new GenericEventHandler<MyEvent>(MyEvent.class);
        bus.registerEventHandler(MyEvent.class, eventHandler);
        MyEvent event1 = new MyEvent("A");
        bus.start();
        bus.publish(event1);
        MyEvent got1 = eventHandler.waitUntilInvokedOrTimeout(5);
        Assert.assertEquals(event1.getMsg(), got1.getMsg());

        bus.publish(event1);
        MyEvent got2 = eventHandler.waitUntilInvokedOrTimeout(5);
        Assert.assertEquals(event1.getMsg(), got2.getMsg());

        bus.stop();
    }

    public void testEventHandlerRegisteredAfterEventPublished(EventBus bus) throws Exception
    {
        GenericEventHandler<MyEvent> eventHandler = new GenericEventHandler<MyEvent>(MyEvent.class);

        MyEvent event1 = new MyEvent("A");
        bus.start();
        bus.publish(event1);
        Thread.sleep(1000);
        bus.registerEventHandler(MyEvent.class, eventHandler);
        try {
            eventHandler.waitUntilInvokedOrTimeout(5);
            Assert.fail("should not be here");
        } catch (TimeoutException te)
        {
            te.printStackTrace();
        }

        finally {
            bus.stop();
        }
    }

    public void testMultipleEventHandler(EventBus bus) throws Exception
    {
        GenericEventHandler<MyEvent> eventHandlerA1 = new GenericEventHandler<MyEvent>(MyEvent.class);
        GenericEventHandler<MyEvent> eventHandlerA2 = new GenericEventHandler<MyEvent>(MyEvent.class);
        GenericEventHandler<MyEvent2> eventHandlerB = new GenericEventHandler<MyEvent2>(MyEvent2.class);

        bus.registerEventHandler(MyEvent.class, eventHandlerA1);
        bus.registerEventHandler(MyEvent.class, eventHandlerA2);
        bus.registerEventHandler(MyEvent2.class, eventHandlerB);
        MyEvent event1 = new MyEvent("A");
        MyEvent event2 = new MyEvent("B");
        MyEvent2 event3 = new MyEvent2(5);
        bus.start();
        bus.publish(event1);
        MyEvent got1 = eventHandlerA1.waitUntilInvokedOrTimeout(5);
        MyEvent got2 = eventHandlerA2.waitUntilInvokedOrTimeout(5);
        Assert.assertEquals(event1.getMsg(), got1.getMsg());
        Assert.assertEquals(event1.getMsg(), got2.getMsg());

        bus.publish(event2);
        bus.publish(event3);
        got1 = eventHandlerA1.waitUntilInvokedOrTimeout(1, 5);
        got2 = eventHandlerA2.waitUntilInvokedOrTimeout(1, 5);
        MyEvent2 got3 = eventHandlerB.waitUntilInvokedOrTimeout(5);
        Assert.assertEquals(event2.getMsg(), got1.getMsg());
        Assert.assertEquals(event2.getMsg(), got2.getMsg());
        Assert.assertEquals(event3.getNumber(), got3.getNumber());

        bus.stop();
    }

    public class MyEvent implements Event
    {
        String msg;

        public MyEvent(String msg)
        {
            this.msg = msg;
        }
        
        public String getMsg()
        {
            return msg;
        }
    }

    public class MyEvent2 implements Event
    {
        int number;

        public MyEvent2(int number)
        {
            this.number = number;
        }

        public int getNumber()
        {
            return number;
        }
    }
}
