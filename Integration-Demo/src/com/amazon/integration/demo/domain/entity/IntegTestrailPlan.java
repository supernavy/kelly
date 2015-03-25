package com.amazon.integration.demo.domain.entity;

import com.amazon.infra.domain.Entity;


public class IntegTestrailPlan
{
    public enum Status {New, Ready, TestInProgress, End};
    
    Entity<IntegBuildQA> integBuildQAInfo;
    Long testrailPlanId;
    Status status;
    
    public IntegTestrailPlan(Entity<IntegBuildQA> integBuildQAInfo)
    {
        this(integBuildQAInfo, null, Status.New);
    }

    private IntegTestrailPlan(Entity<IntegBuildQA> integBuildQAInfo, Long testrailPlanId, Status status)
    {
        this.integBuildQAInfo = integBuildQAInfo;
        this.testrailPlanId = testrailPlanId;
        this.status = status;
    }

    public Entity<IntegBuildQA> getIntegBuildQAInfo()
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
    
    public IntegTestrailPlan updateTestrailPlanId(Long testrailPlanId)
    {
        return new IntegTestrailPlan(getIntegBuildQAInfo(), testrailPlanId, Status.Ready);
    }
    
    public IntegTestrailPlan start()
    {
        return new IntegTestrailPlan(getIntegBuildQAInfo(), getTestrailPlanId(), Status.TestInProgress);
    }
    
    public IntegTestrailPlan end()
    {
        return new IntegTestrailPlan(getIntegBuildQAInfo(), getTestrailPlanId(), Status.End);
    }
}
