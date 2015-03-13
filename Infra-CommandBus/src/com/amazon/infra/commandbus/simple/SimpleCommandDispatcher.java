package com.amazon.infra.commandbus.simple;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import com.amazon.infra.commandbus.Command;
import com.amazon.infra.commandbus.CommandExecution;
import com.amazon.infra.commandbus.CommandHandler;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.repository.RepositoryException;


/**
 * This dispatcher only has single thread to dispatch command, so the command is sured to be executed
 * in the sequence of they are received.
 * @author lihaijun
 *
 */
public class SimpleCommandDispatcher
{
    SimpleCommandBus simpleCommandBus;
    ExecutorService executionService;
    Dispatcher dispatcher;

    Logger logger = Logger.getLogger(SimpleCommandDispatcher.class.getName());

    public SimpleCommandDispatcher(SimpleCommandBus simpleCommandBus)
    {
        this.simpleCommandBus = simpleCommandBus;
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

        public void stop() {
            stop = true;
        }
        
       
        @Override
        public void run()
        {
            logger.finest("dispatcher running"+this);
            while (!stop) {
                Entity<CommandExecution<?>> commandExecution = null;
                try {
                    Thread.sleep(100);
                    commandExecution = simpleCommandBus.takeOne();
                    if (commandExecution == null)
                        continue;
                    Date submitTime = commandExecution.getData().getSubmitTime();
                    Date currDate = Calendar.getInstance().getTime();
                    if(currDate.before(new Date(submitTime.getTime()+SimpleCommandBus.EXECUTION_TIMEOUT_SECS*1000)))
                    {
                        CommandHandler<? extends Command<?>, ?> handler = simpleCommandBus.getHandler(commandExecution.getData().getCommand().getClass());
                        if (handler != null) {
                            logger.fine(String.format("dispatch command[%s][%s] to handler[%s]", commandExecution.getId(), commandExecution.getData(), handler));
                            Method m = handler.getClass().getMethod("handle", new Class<?>[] {handler.getCommandType()});
                            Object result = m.invoke(handler, commandExecution.getData().getCommand());
                            simpleCommandBus.returnOne(commandExecution.getId(), commandExecution.getData().getCommand().getReturnType().cast(result));
                        } else {
                            simpleCommandBus.putbackOneIntoQueue(commandExecution.getId());
                        }
                    } else {
                        simpleCommandBus.timeoutOne(commandExecution.getId());
                    }                   
                } catch (Exception e) {
                    e.printStackTrace();
                    if (commandExecution != null) {
                        try {
                            simpleCommandBus.exceptionOne(commandExecution.getId(), e);
                        } catch (RepositoryException e1) {
                            throw new RuntimeException(e1);
                        }
                    }
                }
            }
        }
    }
}
