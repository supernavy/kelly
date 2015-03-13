package com.amazon.infra.commandbus;

import java.util.Calendar;
import java.util.Date;

public class CommandExecution<R>
{
    public static enum Status 
    {
        NotStarted(false,false), Running(true,false), Returned(true,true), ThrownException(true,true), Aborted(true,true), Timeout(true, true);
        
        boolean ended;
        boolean started;
        private Status(boolean started, boolean ended)
        {
            this.started = started;
            this.ended = ended;
        }
        
        public boolean isEnded() {
            return ended;
        }
        
        public boolean isStarted() {
            return started;
        }
    };
    
    Command<R> command;
    CommandHandler<? extends Command<?>, ?> handler;
    R result;
    Exception ex;
    Status status;
    Date submitTime;
    
    public CommandExecution(Command<R> command, CommandHandler<? extends Command<?>, ?> handler)
    {
        this.command = command;
        this.handler = handler;
        this.status = Status.NotStarted;
        this.submitTime = Calendar.getInstance().getTime();
    }
    
    public Command<R> getCommand()
    {
        return command;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public CommandHandler<? extends Command<?>, ?> getHandler()
    {
        return handler;
    }
    
    public void setResult(Object result)
    {
        this.result = command.getReturnType().cast(result);
        setStatus(Status.Returned);
    }
    
    public R getResult()
    {
        return result;
    }
    
    public void setEx(Exception ex)
    {
        this.ex = ex;
        setStatus(Status.ThrownException);
    }
    
    public void setStatus(Status status)
    {
        this.status = status;
    }
    
    public Exception getEx()
    {
        return ex;
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    @Override
    public String toString()
    {
        return String.format("command[%s], handler[%s]", command, handler);
    }
}
