package com.amazon.infra.eventbus;

import java.util.Set;
import com.amazon.infra.domain.Entity;

public interface EventBus {
	public <T extends Event> void registerEventHandler(Class<T> eventClass, AbsEventHandler<T> eventHandler);
	public void unregisterAllEventHandler();
	public <T> boolean unregister(Class<T> eventClass, AbsEventHandler<T> commandHandler);
	public <T extends Event> String publish(T event) throws EventBusException;
	public void start() throws EventBusException;
	public void stop() throws EventBusException;
	public Set<Entity<EventDistribution<? extends Event>>> findAllEvents() throws EventBusException;
}
