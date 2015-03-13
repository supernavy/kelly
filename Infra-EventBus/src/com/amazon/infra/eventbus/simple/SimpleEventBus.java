package com.amazon.infra.eventbus.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.eventbus.EventBusException;
import com.amazon.infra.eventbus.EventDistribution;
import com.amazon.infra.eventbus.AbsEventHandler;
import com.amazon.infra.eventbus.EventHandlerDistribution;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class SimpleEventBus implements EventBus
{
    Repository<EventDistribution<? extends Event>> eventDistributionRepository;
    Repository<List<String>> queueRepository;

    Entity<List<String>> queue;
    Map<Class<? extends Event>, List<AbsEventHandler<? extends Event>>> eventHandlers;
    SimpleEventDispatcher dispatcher;

    Logger logger = Logger.getLogger(SimpleEventBus.class.getName());

    public SimpleEventBus(Repository<EventDistribution<? extends Event>> eventDistributionRepository, Repository<List<String>> queueRepository) throws RepositoryException
    {
        this.eventDistributionRepository = eventDistributionRepository;
        this.queueRepository = queueRepository;

        this.eventHandlers = new HashMap<Class<? extends Event>, List<AbsEventHandler<? extends Event>>>();
        dispatcher = new SimpleEventDispatcher(this);
    }

    @Override
    public <T extends Event> void registerEventHandler(Class<T> eventClass, AbsEventHandler<T> eventHandler)
    {
        List<AbsEventHandler<? extends Event>> handlers = eventHandlers.get(eventClass);
        if (handlers == null) {
            handlers = new ArrayList<AbsEventHandler<? extends Event>>();
            eventHandlers.put(eventClass, handlers);
        }
        handlers.add(eventHandler);
        logger.info(String.format("Registered EventHandler[%s] with eventType[%s]", eventHandler, eventHandler.getEventType()));
    }

    @Override
    public void unregisterAllEventHandler()
    {
        eventHandlers.clear();
    }

    @Override
    public <T> boolean unregister(Class<T> eventClass, AbsEventHandler<T> commandHandler)
    {
        List<AbsEventHandler<? extends Event>> handlers = eventHandlers.get(eventClass);
        if (handlers == null)
            return false;
        return handlers.remove(commandHandler);
    }

    @Override
    public <T extends Event> String publish(T event) throws EventBusException
    {
        try {
            EventDistribution<T> newEventDistribution = new EventDistribution<T>(event, getEventHanderDistributions(event));
            Entity<EventDistribution<? extends Event>> entity = eventDistributionRepository.createEntity(newEventDistribution);
            synchronized (queue) {
                queue.getData().add(entity.getId());
            }
            logger.fine(String.format("bus recieved event[%s] with id[%s]", event, entity.getId()));
            verify(entity.getId());
            return entity.getId();
        } catch (RepositoryException e) {
            throw new EventBusException(e);
        }
    }

    public Entity<EventDistribution<? extends Event>> takeOne() throws RepositoryException
    {
        synchronized (queue) {
            if (queue.getData().size() > 0) {
                String id = queue.getData().remove(0);
                Entity<EventDistribution<? extends Event>> com = eventDistributionRepository.load(id);
                EventDistribution<? extends Event> ed = com.getData().setStatus(EventDistribution.Status.Disseminating);
                com = eventDistributionRepository.updateEntity(id, ed);
                logger.fine(String.format("bus started disseminating event[%s] with id[%s]", com.getData().getEvent(), id));
                return com;
            }
        }
        return null;
    }

    public void verify(String id) throws RepositoryException
    {
        Entity<EventDistribution<? extends Event>> com = eventDistributionRepository.load(id);
        if(com.getData().getStatus().isEnded())
        {
            logger.fine(String.format("EventDistribution status[%s], remove EventDistribution[%s]", com.getData().getStatus(), com));
            eventDistributionRepository.delete(id);
        }
    }
    
    public void discloseOne(String id, EventHandlerDistribution<? extends Event> ehd) throws RepositoryException
    {
        Entity<EventDistribution<? extends Event>> com = eventDistributionRepository.load(id);
        EventDistribution<? extends Event> ed = com.getData().setSubStatus(ehd, EventHandlerDistribution.Status.ConfirmedRecieved);
        com = eventDistributionRepository.updateEntity(id, ed);
        logger.fine(String.format("bus has disclosed event[%s] with id[%s] to Handler[%s]", com.getData().getEvent(), id, ehd.getHandler()));
        verify(id);
    }
    
    public void exceptionOne(String id, EventHandlerDistribution<? extends Event> ehd, Exception e) throws RepositoryException
    {
        Entity<EventDistribution<? extends Event>> com = eventDistributionRepository.load(id);
        EventDistribution<? extends Event> ed = com.getData().setSubStatus(ehd, EventHandlerDistribution.Status.FailedInSending);
        com = eventDistributionRepository.updateEntity(id, ed);
        logger.fine(String.format("bus exception in sending event[%s] with id[%s] to Handler[%s]", com.getData().getEvent(), id, ehd.getHandler()));
        verify(id);
    }

    public <T extends Event> List<EventHandlerDistribution<? extends Event>> getEventHanderDistributions(T e)
    {
        List<EventHandlerDistribution<? extends Event>> ret = new ArrayList<EventHandlerDistribution<? extends Event>>();
        List<AbsEventHandler<? extends Event>> handlers = getHandlers(e.getClass());
        if(handlers!=null) {
            for (AbsEventHandler<? extends Event> h : handlers) {
                EventHandlerDistribution<T> d = new EventHandlerDistribution<T>(e, h);
                ret.add(d);
            }
        }
        return ret;
    }

    public <T extends Event> List<AbsEventHandler<? extends Event>> getHandlers(Class<T> cls)
    {
        List<AbsEventHandler<? extends Event>> ret = new ArrayList<AbsEventHandler<? extends Event>>();
        for(Map.Entry<Class<? extends Event>, List<AbsEventHandler<? extends Event>>> entry: eventHandlers.entrySet())
        {
            Class<? extends Event> eventType = entry.getKey();
            List<AbsEventHandler<? extends Event>> handlers = entry.getValue();
            if(cls.isAssignableFrom(eventType))
            {
                ret.addAll(handlers);
            }
        }
        return ret;
    }

    @Override
    public void start() throws EventBusException
    {
        try {
            queue = queueRepository.createEntity(new ArrayList<String>());
            dispatcher.start();
        } catch (RepositoryException e) {
            throw new EventBusException(e);
        }
    }

    @Override
    public void stop() throws EventBusException
    {
        try {
            queueRepository.updateEntity(queue.getId(), queue.getData());
            dispatcher.stop();
        } catch (RepositoryException e) {
            throw new EventBusException(e);
        }
    }
}
