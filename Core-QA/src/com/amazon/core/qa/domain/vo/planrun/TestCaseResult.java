package com.amazon.core.qa.domain.vo.planrun;

import java.util.Date;

public class TestCaseResult
{
    public static enum Status {
        NotRun(false, false), Running(true, false), Pass(true, true), Fail(true, true);
        
        boolean started;
        boolean completed;
        private Status(boolean started, boolean completed)
        {
            this.completed = completed;
            this.started = started;
        }
        public boolean isStarted()
        {
            return started;
        }
        public boolean isCompleted()
        {
            return completed;
        } 
    };
    
    Status status;
    String message;
    Date startTime;
    Date endTime;
    public TestCaseResult(Status status, String message, Date startTime, Date endTime)
    {
        this.status = status;
        this.message = message;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Status getStatus()
    {
        return status;
    }
    public String getMessage()
    {
        return message;
    }
    public Date getStartTime()
    {
        return startTime;
    }
    public Date getEndTime()
    {
        return endTime;
    }    
}
