package com.amazon.infra.commandbus.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import com.amazon.infra.commandbus.Command;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.commandbus.CommandExecution;
import com.amazon.infra.commandbus.CommandHandler;
import com.amazon.infra.commandbus.CommandResult;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class SimpleCommandBus implements CommandBus
{
    public static int EXECUTION_TIMEOUT_SECS=10;
    
    Logger logger = Logger.getLogger(this.getClass().getName());

    Repository<CommandExecution<?>> commandExecutionRepository;
    Repository<List<String>> queueRepository;

    Entity<List<String>> queue;
    Map<Class<? extends Command<?>>, CommandHandler<? extends Command<?>, ?>> handlerMap;
    SimpleCommandDispatcher dispatcher;
    
    ExecutorService executionService;

    boolean stopped = true;
    public SimpleCommandBus(Repository<CommandExecution<?>> persistence, Repository<List<String>> queueRepository) throws RepositoryException
    {
        this.commandExecutionRepository = persistence;
        this.queueRepository = queueRepository;
        this.handlerMap = new HashMap<Class<? extends Command<?>>, CommandHandler<? extends Command<?>, ?>>();
        dispatcher = new SimpleCommandDispatcher(this);
        executionService = Executors.newCachedThreadPool();       
    }

    @Override
    public <T extends Command<R>, R> void register(Class<T> commandType, CommandHandler<? extends T, ? extends R> commandHandler)
    {
        handlerMap.put(commandType, commandHandler);
    }

    @Override
    public <T extends Command<?>> void unregister(Class<T> command)
    {
        handlerMap.remove(command);
    }

    @Override
    public void unregisterAll()
    {
        Iterator<Class<? extends Command<?>>> it = handlerMap.keySet().iterator();
        while (it.hasNext()) {
            Class<? extends Command<?>> cls = it.next();
            handlerMap.remove(cls);
        }
    }

    @Override
    public <T extends Command<R>, R> String send(T command) throws CommandBusException
    {
        try {
            if(!stopped)
            {
                CommandExecution<R> commandExecution = new CommandExecution<R>(command, getHandler(command.getClass()));
                Entity<CommandExecution<?>> entity = commandExecutionRepository.createEntity(commandExecution);
                queue.getData().add(entity.getId());
                logger.fine(String.format("bus recieved command[%s] with id[%s]", command, entity.getId()));
                return entity.getId();
            } else {
                throw new CommandBusException("Command bus stopped. Can't accept command");
            }
        } catch (RepositoryException e) {
            throw new CommandBusException(e);
        }
    }

    public Entity<CommandExecution<?>> takeOne() throws RepositoryException
    {
        if (queue.getData().size() > 0) {
            String id = queue.getData().remove(0);
            Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
            com.getData().setStatus(CommandExecution.Status.Running);
            commandExecutionRepository.updateEntity(id, com.getData());
            logger.fine(String.format("bus started dispatching command[%s] with id[%s]", com.getData().getCommand(), id));
            return com;
        }
        return null;
    }

    public void putbackOneIntoQueue(String id) throws RepositoryException
    {

        Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
        com.getData().setStatus(CommandExecution.Status.NotStarted);
        commandExecutionRepository.updateEntity(id, com.getData());
        queue.getData().add(0, com.getId());
        logger.fine(String.format("bus put back command[%s] with id[%s]", com.getData().getCommand(), id));
    }

    public void returnOne(String id, Object result) throws RepositoryException
    {
        Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
        com.getData().setResult(result);
        commandExecutionRepository.updateEntity(id, com.getData());
        logger.fine(String.format("bus has returned result[%s] for command[%s] with id[%s]", result, com.getData().getCommand(), id));
    }

    public void abortOne(String id) throws RepositoryException
    {
        Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
        com.getData().setStatus(CommandExecution.Status.Aborted);
        commandExecutionRepository.updateEntity(id, com.getData());
        logger.fine(String.format("bus has aborted for command[%s] with id[%s]", com.getData().getCommand(), id));
    }
    
    public void timeoutOne(String id) throws RepositoryException
    {
        Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
        com.getData().setStatus(CommandExecution.Status.Timeout);
        commandExecutionRepository.updateEntity(id, com.getData());
        logger.fine(String.format("bus has timeout for command[%s] with id[%s]", com.getData().getCommand(), id));
    }

    public void exceptionOne(String id, Exception ex) throws RepositoryException
    {
        Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
        com.getData().setEx(ex);
        commandExecutionRepository.updateEntity(id, com.getData());
        logger.fine(String.format("bus has throw exception[%s] for command[%s] with id[%s]", ex, com.getData().getCommand(), id));
    }

    public <T extends Command<?>> CommandHandler<? extends Command<?>, ?> getHandler(Class<T> cls)
    {
        for (Map.Entry<Class<? extends Command<?>>, CommandHandler<? extends Command<?>, ?>> entry : handlerMap.entrySet()) {
            Class<? extends Command<?>> commandType = entry.getKey();
            if (cls.equals(commandType)) {
                CommandHandler<? extends Command<?>, ?> h = entry.getValue();
                return h;
            } else if (commandType.isAssignableFrom(cls)) {
                CommandHandler<? extends Command<?>, ?> h = entry.getValue();
                return h;
            }
        }
        return null;
    }

    @Override
    public void start() throws CommandBusException
    {
        try {
            queue = queueRepository.createEntity(new ArrayList<String>());
            dispatcher.start();
            stopped = false;
        } catch (RepositoryException re) {
            throw new CommandBusException(re);
        }
    }

    @Override
    public void stop() throws CommandBusException
    {
        try {
            queueRepository.updateEntity(queue.getId(), queue.getData());
            dispatcher.stop();
            stopped = true;
        } catch (RepositoryException re) {
            throw new CommandBusException(re);
        }
    }

    @Override
    public CommandExecution<?> getCommandExecution(String id) throws CommandBusException
    {
        try {
            Entity<CommandExecution<?>> com = commandExecutionRepository.load(id);
            return com.getData();
        } catch (RepositoryException e) {
            throw new CommandBusException(e);
        }
    }

    @Override
    public <T extends Command<R>, R> CommandResult<R> submit(T command) throws CommandBusException
    {
        String id = send(command);
        CommandResult<R> commandResult = new CommandResult<>(this, executionService, id, command.getReturnType());
        return commandResult;
    }
}
