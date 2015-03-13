package com.amazon.infra.system;

import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;

public interface AppSystem
{
    public enum Layer {Core, Extension};
    
    public String getName();
    public Layer getLayer();
    public void start() throws AppSystemException;
    public void shutdown() throws AppSystemException;
    
    public CommandBus getCommandBus();
    public EventBus getEventBus();
    
    public AppSystem getDependency(String name) throws AppSystemException;

    public <T> Repository<T> getRepository(String name) throws AppSystemException;    
    public void addSystemListener(AppSystemListener listener) throws AppSystemException;
}
