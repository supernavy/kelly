package com.amazon.infra.eventbus.simple;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventBusException;
import com.amazon.infra.eventbus.EventDistribution;
import com.amazon.infra.eventbus.EventHandlerDistribution;
import com.amazon.infra.repository.RepositoryException;

/**
 * This dispatcher only has single thread to dispatch event, so the event is sured to be broadcasted
 * in the sequence of they are received.
 * @author lihaijun
 *
 */
public class SimpleEventDispatcher
{
    SimpleEventBus simpleEventBus;
    ExecutorService executionService;
    Dispatcher dispatcher;
    Logger logger = Logger.getLogger(SimpleEventDispatcher.class.getName());

    public SimpleEventDispatcher(SimpleEventBus simpleEventBus)
    {
        this.simpleEventBus = simpleEventBus;
    }

    public void start()
    {
        executionService = Executors.newSingleThreadExecutor();
        dispatcher = new Dispatcher();
        executionService.execute(dispatcher);
    }

    public void stop()
    {
        if(dispatcher!=null)
        {
            dispatcher.stop();
        }
        executionService.shutdown();
        
        while(!executionService.isTerminated())
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            logger.finest("dispatcher is stopping");
        }
        logger.finest("dispatcher stopped");
    }

    public class Dispatcher implements Runnable
    {
        boolean stop = false;
        
        public void stop()
        {
            this.stop = true;
        }
        
        @Override
        public void run()
        {
            logger.finest("dispatcher running");
            while (!stop) {
                Entity<EventDistribution<? extends Event>> eventDistribution = null;
                EventHandlerDistribution<? extends Event> ehd = null;
                try {
                    Thread.sleep(100);
                    eventDistribution = simpleEventBus.takeOne();
                    if (eventDistribution == null)
                        continue;
                    logger.fine(String.format("dispatching id[%s] event[%s]", eventDistribution.getId(), eventDistribution.getData().getEvent()));
                    List<EventHandlerDistribution<? extends Event>> subDistributions = eventDistribution.getData().getSubDistributions();
                    if (subDistributions != null) {
                        int handlersNumber = subDistributions.size();
                        logger.fine(String.format("handler numbers %s for %s", handlersNumber, eventDistribution));
                            
                        for (int i = 0; i < handlersNumber; i++) {
                            ehd = subDistributions.get(i);
                            logger.fine(String.format("dispatch id[%s] event[%s] to handler[%s]", eventDistribution.getId(), eventDistribution.getData().getEvent(), ehd.getHandler()));
                            Method m = null;
                            try {
                                m = ehd.getHandler().getClass().getMethod("handle", new Class<?>[] { ehd.getHandler().getEventType() });
                            } catch (NoSuchMethodException nsme)
                            {
                                for(Method t : ehd.getHandler().getClass().getMethods())
                                {
                                    if(t.getName().equals("handle"))
                                    {
                                        m = t;
                                        break;
                                    }
                                }
                            }
                            if(m == null)
                            {
                                throw new EventBusException(String.format("can't find event handle method for event. handlerClass[%s] eventClass[%s]", ehd.getHandler().getClass(), eventDistribution.getData().getEvent()));
                            }
                            
                            m.invoke(ehd.getHandler(), eventDistribution.getData().getEvent());
                            simpleEventBus.discloseOne(eventDistribution.getId(), ehd);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (eventDistribution != null && ehd != null) {
                        try {
                            simpleEventBus.exceptionOne(eventDistribution.getId(), ehd, e);
                        } catch (RepositoryException e1) {
                            throw new RuntimeException(e1);
                        }
                    }
                } 
            }
        }
    }
}
