package com.amazon.infra.eventbus;

public class EventHandlerDistribution<T extends Event>
{
    public enum Status {
        New(false), NeedResend(false), ConfirmedRecieved(true), FailedInSending(true);
        boolean ended;
        private Status(boolean ended)
        {
           this.ended = ended;
        }
        
        public boolean isEnded()
        {
            return this.ended;
        }
    }
    
    T event;
    AbsEventHandler<? extends Event> handler;
    Status status;
    Exception ex;
    
    public EventHandlerDistribution(T event, AbsEventHandler<? extends Event> handler)
    {
        this.event = event;
        this.handler = handler;
        this.status = Status.New;
    }
    
    public T getEvent()
    {
        return event;
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    public void setStatus(Status status)
    {
        this.status = status;
    }
    
    public AbsEventHandler<? extends Event> getHandler()
    {
        return handler;
    }
    
    public void setEx(Exception ex)
    {
        this.ex = ex;
    }
}
