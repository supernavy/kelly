package com.amazon.infra.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandExecution;
import com.amazon.infra.commandbus.simple.SimpleCommandBus;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.eventbus.EventDistribution;
import com.amazon.infra.eventbus.simple.SimpleEventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.repository.impl.RepositoryMemoryImpl;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.AppSystemListener;

public class SimpleAppSystemImpl implements AppSystem
{
    String name;
    Layer layer;
    List<AppSystemListener> systemListerns = new ArrayList<AppSystemListener>();
    CommandBus commandBus;
    EventBus eventBus;
    Map<String, AppSystem> systemDependencies = new HashMap<String,AppSystem>();
    Map<String, Repository<?>> repositories = new HashMap<String, Repository<?>>();

    public SimpleAppSystemImpl(String name, Layer layer) throws AppSystemException
    {
        this.name = name;
        this.layer = layer;
        this.commandBus = createCommandBus();
        this.eventBus = createEventBus();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Layer getLayer()
    {
        return layer;
    }

    @Override
    public void start() throws AppSystemException
    {
        for(AppSystemListener l: systemListerns)
        {
            l.onStart(this);
        }
        try {
            if (eventBus != null) {
                eventBus.start();
            }
            if (commandBus != null) {
                commandBus.start();
            }
        } catch (Exception e) {
            throw new AppSystemException(e);
        }
    }


    @Override
    public void shutdown() throws AppSystemException
    {
        for(AppSystemListener l: systemListerns)
        {
            l.onShutdown(this);
        }
        try {
            if (commandBus != null) {
                commandBus.stop();
            }
            if (eventBus != null) {
                eventBus.stop();
            }
        } catch (Exception e) {
            throw new AppSystemException(e);
        }
    }

    @Override
    public CommandBus getCommandBus()
    {
        return this.commandBus;
    }

    @Override
    public EventBus getEventBus()
    {
        return this.eventBus;
    }

    private CommandBus createCommandBus() throws AppSystemException
    {
        Repository<CommandExecution<?>> r1 = createRepository();
        Repository<List<String>> r2 = createRepository();
        try {
            CommandBus commandBus = new SimpleCommandBus(r1, r2);
            return commandBus;
        } catch (RepositoryException e) {
            throw new AppSystemException(e);
        }
    }

    private EventBus createEventBus() throws AppSystemException
    {
        Repository<EventDistribution<? extends Event>> eventDistributionRepository = createRepository();
        Repository<List<String>> eventQueueRepository = createRepository();

        EventBus eventBus;
        try {
            eventBus = new SimpleEventBus(eventDistributionRepository, eventQueueRepository);
            return eventBus;
        } catch (RepositoryException e) {
            throw new AppSystemException(e);
        }     
    }

    public <T> Repository<T> createRepository() throws AppSystemException
    {
        Repository<T> repo = new RepositoryMemoryImpl<T>();
        return repo;
    }

    @Override
    public void addSystemListener(AppSystemListener listener) throws AppSystemException
    {
        this.systemListerns.add(listener);
    }
    
    protected void setDependency(String name, AppSystem system)
    {
        systemDependencies.put(name, system);
    }

    @Override
    public AppSystem getDependency(String name) throws AppSystemException
    {
        return systemDependencies.get(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Repository<T> getRepository(String name) throws AppSystemException
    {
        Repository<T> repo = (Repository<T>) repositories.get(name);
        if(repo == null)
        {
            repo = createRepository();
            repositories.put(name, repo);
        }
        return repo;
    }
}
