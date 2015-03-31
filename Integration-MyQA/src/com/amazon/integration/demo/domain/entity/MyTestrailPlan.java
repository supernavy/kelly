package com.amazon.integration.demo.domain.entity;

import com.amazon.infra.domain.Entity;


public class MyTestrailPlan
{
    public enum Status {New, Ready, TestInProgress, End};
    
    Entity<MyBuildQA> integBuildQAInfo;
    Long testrailPlanId;
    Status status;
    
    public MyTestrailPlan(Entity<MyBuildQA> integBuildQAInfo)
    {
        this(integBuildQAInfo, null, Status.New);
    }

    private MyTestrailPlan(Entity<MyBuildQA> integBuildQAInfo, Long testrailPlanId, Status status)
    {
        this.integBuildQAInfo = integBuildQAInfo;
        this.testrailPlanId = testrailPlanId;
        this.status = status;
    }

    public Entity<MyBuildQA> getMyBuildQAInfo()
    {
        return integBuildQAInfo;
    }

    public Long getTestrailPlanId()
    {
        return testrailPlanId;
    }

    public Status getStatus()
    {
        return status;
    }
    
    public MyTestrailPlan updateTestrailPlanId(Long testrailPlanId)
    {
        return new MyTestrailPlan(getMyBuildQAInfo(), testrailPlanId, Status.Ready);
    }
    
    public MyTestrailPlan start()
    {
        return new MyTestrailPlan(getMyBuildQAInfo(), getTestrailPlanId(), Status.TestInProgress);
    }
    
    public MyTestrailPlan end()
    {
        return new MyTestrailPlan(getMyBuildQAInfo(), getTestrailPlanId(), Status.End);
    }
}
