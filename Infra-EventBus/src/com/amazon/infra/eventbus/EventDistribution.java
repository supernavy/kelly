package com.amazon.infra.eventbus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class EventDistribution<T extends Event>
{
    Logger logger = Logger.getLogger(EventDistribution.class.getName());
    public enum Status 
    {
        Init(false,false), Disseminating(true,false), PartiallyDisclosed(true,false), CompletelyDisclosed(true,true), Dropped(true, true);
        
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
    
    T event;
    Status status;
    Date happenTime;
    List<EventHandlerDistribution<? extends Event>> errorSubDistributions = new ArrayList<EventHandlerDistribution<? extends Event>>();
    List<EventHandlerDistribution<? extends Event>> doneSubDistributions = new ArrayList<EventHandlerDistribution<? extends Event>>();
    List<EventHandlerDistribution<? extends Event>> subDistributions = new ArrayList<EventHandlerDistribution<? extends Event>>();
    
    public EventDistribution(T event, List<EventHandlerDistribution<? extends Event>> subDistributions)
    {
        this(event, subDistributions.size()>0?Status.Init:Status.Dropped, subDistributions, null, null);
    }
    
    public EventDistribution(T event, Status status, List<EventHandlerDistribution<? extends Event>> subDistributions, List<EventHandlerDistribution<? extends Event>> doneSubDistributions, List<EventHandlerDistribution<? extends Event>> errorSubDistributions)
    {
        this.event = event;
        this.status = status;
        if(subDistributions!=null)
            this.subDistributions.addAll(subDistributions);
        if(doneSubDistributions!=null)
            this.doneSubDistributions.addAll(doneSubDistributions);
        if(errorSubDistributions!=null)
            this.errorSubDistributions.addAll(errorSubDistributions);
        
        happenTime = Calendar.getInstance().getTime();
    }
    
    public EventDistribution<T> setStatus(Status status)
    {
        return new EventDistribution<T>(getEvent(), status, getSubDistributions(), getDoneSubDistributions(), getErrorSubDistributions());
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    public Date getHappenTime()
    {
        return happenTime;
    }
    
    public T getEvent()
    {
        return event;
    }
    
    public EventDistribution<T> setSubStatus(EventHandlerDistribution<? extends Event> eventHandlerDistribution, EventHandlerDistribution.Status status)
    {
        List<EventHandlerDistribution<? extends Event>> newErrorSubDistributions = getErrorSubDistributions();
        List<EventHandlerDistribution<? extends Event>> newDoneSubDistributions = getDoneSubDistributions();
        List<EventHandlerDistribution<? extends Event>> newSubDistributions = getSubDistributions();
        Status newStatus = getStatus();
        if(eventHandlerDistribution !=null)
        {
            eventHandlerDistribution.setStatus(status);
            if(status.isEnded())
            {
                newSubDistributions.remove(eventHandlerDistribution);
            }  
            
            if(status == EventHandlerDistribution.Status.ConfirmedRecieved)
            {
                newDoneSubDistributions.add(eventHandlerDistribution);
            }
            
            if(status == EventHandlerDistribution.Status.FailedInSending)
            {
                newErrorSubDistributions.add(eventHandlerDistribution);
            }
        }
        
        if(newSubDistributions.size()==0)
        {
            if(newErrorSubDistributions.size()==0)
            {
                newStatus = Status.CompletelyDisclosed;
            }
            else
            {
                newStatus = Status.PartiallyDisclosed;
            }
            logger.fine(String.format("Sending event[%s] to All Handlers, recieved handlers[%d], error handlers[%d], remaining handlers[%d]", getEvent(), getDoneSubDistributions().size(), getErrorSubDistributions().size(), getSubDistributions().size()));
        }
        
        return new EventDistribution<T>(getEvent(), newStatus, newSubDistributions, newDoneSubDistributions, newErrorSubDistributions);
        
    }
    
    public List<EventHandlerDistribution<? extends Event>> getSubDistributions()
    {
        return new ArrayList<EventHandlerDistribution<? extends Event>>(subDistributions);
    }
    
    public List<EventHandlerDistribution<? extends Event>> getDoneSubDistributions()
    {
        return new ArrayList<EventHandlerDistribution<? extends Event>>(doneSubDistributions);
    }
    public List<EventHandlerDistribution<? extends Event>> getErrorSubDistributions()
    {
        return new ArrayList<EventHandlerDistribution<? extends Event>>(errorSubDistributions);
    }
}
