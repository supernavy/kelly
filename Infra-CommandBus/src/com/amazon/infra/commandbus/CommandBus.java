package com.amazon.infra.commandbus;

import java.util.Set;
import com.amazon.infra.domain.Entity;

public interface CommandBus {
	public <T extends Command<R>, R> void register(Class<T> comnandType, CommandHandler<? extends T, ? extends R> commandHandler);
	public void unregisterAll();
	public <T extends Command<?>> void unregister(Class<T> cls);
	public <T extends Command<R>, R> String send(T command) throws CommandBusException;
	public <T extends Command<R>, R> CommandResult<R> submit(T command) throws CommandBusException;
	public CommandExecution<?> getCommandExecution(String id) throws CommandBusException;
	public Set<Entity<CommandExecution<?>>> findAllCommandExecutions() throws CommandBusException;
	public void start() throws CommandBusException;
	public void stop() throws CommandBusException;
}
