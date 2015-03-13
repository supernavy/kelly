package com.amazon.core.qa.domain.vo.planrun;

public class PlanRunResult
{
    int running;
    int pass;
    int fail;
    int abort;
    int skip;
    int timeout;
    int total;
    
    public PlanRunResult(int total)
    {
        this(0,0,0,0,0,0,total);
    }
    
    public PlanRunResult(int running, int pass, int fail, int abort, int skip, int timeout, int total)
    {
        this.running = running;
        this.pass = pass;
        this.fail = fail;
        this.abort = abort;
        this.skip = skip;
        this.timeout = timeout;
        this.total = total;
    }
    
    public PlanRunResult increaseRunning(){
        return new PlanRunResult(running+1, pass, fail, abort, skip, timeout, total);
    }
    
    public PlanRunResult increasePass(){
        return new PlanRunResult(running-1, pass+1, fail, abort, skip, timeout, total);
    }
    
    public PlanRunResult increaseFail(){
        return new PlanRunResult(running-1, pass, fail+1, abort, skip, timeout, total);
    }
    
    public PlanRunResult increaseAbort(){
        return new PlanRunResult(running-1, pass, fail, abort+1, skip, timeout, total);
    }
    
    public PlanRunResult increaseSkip(){
        return new PlanRunResult(running, pass, fail, abort, skip+1, timeout, total);
    }
    
    public PlanRunResult increaseTimeout(){
        return new PlanRunResult(running-1, pass, fail, abort, skip, timeout+1, total);
    }
}
