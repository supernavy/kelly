package com.amazon.integration.demo.domain.entity;

import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.domain.Entity;


public class IntegBuildQA
{
    public enum Status {New, Preparing, Testing, End};
    
    Entity<BuildQA> buildQAInfo;
    Entity<IntegProductQA> integProductQAInfo;
    Status status;
    
    Entity<IntegTestrailPlan> integTestrailPlanInfo;

    
    public IntegBuildQA(Entity<BuildQA> buildQAInfo, Entity<IntegProductQA> integProductQAInfo)
    {
        this(buildQAInfo, integProductQAInfo, Status.New, null);
    }

    private IntegBuildQA(Entity<BuildQA> buildQAInfo, Entity<IntegProductQA> integProductQAInfo, Status status, Entity<IntegTestrailPlan> integTestrailPlanInfo)
    {
        this.buildQAInfo = buildQAInfo;
        this.integProductQAInfo = integProductQAInfo;
        this.status = status;
        this.integTestrailPlanInfo = integTestrailPlanInfo;
    }

    public Entity<BuildQA> getBuildQAInfo()
    {
        return buildQAInfo;
    }

    public Entity<IntegProductQA> getIntegProductQAInfo()
    {
        return integProductQAInfo;
    }

    public Status getStatus()
    {
        return status;
    }

    public String getName() {
        return String.format("IntegBuildQA for %s", getBuildQAInfo().getData().getName());
    }
    
    public Entity<IntegTestrailPlan> getIntegTestrailPlanInfo()
    {
        return integTestrailPlanInfo;
    }
    
    public IntegBuildQA updateTestrailPlanInfo(Entity<IntegTestrailPlan> integTestrailPlanInfo)
    {
        return new IntegBuildQA(getBuildQAInfo(), getIntegProductQAInfo(), Status.Testing, integTestrailPlanInfo);
    }
    
    public IntegBuildQA prepare()
    {
        return new IntegBuildQA(getBuildQAInfo(), getIntegProductQAInfo(), Status.Preparing, null);
    }
    
    public IntegBuildQA end()
    {
        return new IntegBuildQA(getBuildQAInfo(), getIntegProductQAInfo(), Status.End, getIntegTestrailPlanInfo());
    }
}
