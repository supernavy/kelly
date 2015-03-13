package com.amazon.infra.commandbus;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CommandResult<T>
{
    public static int TIMEOUT_SECS = 30;
    CommandBus commandBus;
    ExecutorService executionService;
    String commandExecutionId;
    Class<T> returnType;
    
    public CommandResult(CommandBus commandBus, ExecutorService executionService, String commandExecutionId, Class<T> returnType)
    {
        this.commandBus = commandBus;
        this.executionService = executionService;
        this.commandExecutionId = commandExecutionId;
        this.returnType = returnType;
    }

    public T getResult() throws CommandException
    {
        return getResult(TIMEOUT_SECS);
    }
    
    public T getResult(int timeoutSecs) throws CommandException
    {
        Future<T> f = executionService.submit(new Callable<T>()
        {
            @Override
            public T call() throws Exception
            {
                while(true) {
                    CommandExecution<?> commandExecution = commandBus.getCommandExecution(commandExecutionId);
                    if(commandExecution.getStatus().isEnded())
                    {
                        if(CommandExecution.Status.Returned == commandExecution.getStatus())
                        {
                            return returnType.cast(commandExecution.getResult());
                        }
                        if(CommandExecution.Status.ThrownException == commandExecution.getStatus())
                        {
                            throw commandExecution.getEx();
                        }
                        throw new CommandException(String.format("Command[%s] is ended with status[%s]", commandExecution.getCommand(), commandExecution.getStatus()));
                    } else {
                        Thread.sleep(1000);
                    }
                }
            }
        });
        
        try {
            return f.get(timeoutSecs, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
