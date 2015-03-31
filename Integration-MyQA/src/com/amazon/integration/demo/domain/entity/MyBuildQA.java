package com.amazon.integration.demo.domain.entity;

import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.domain.Entity;


public class MyBuildQA
{
    public enum Status {New, Preparing, Testing, End};
    
    Entity<BuildQA> buildQAInfo;
    Entity<MyProductQA> integProductQAInfo;
    Status status;
    
    Entity<MyTestrailPlan> integTestrailPlanInfo;

    
    public MyBuildQA(Entity<BuildQA> buildQAInfo, Entity<MyProductQA> integProductQAInfo)
    {
        this(buildQAInfo, integProductQAInfo, Status.New, null);
    }

    private MyBuildQA(Entity<BuildQA> buildQAInfo, Entity<MyProductQA> integProductQAInfo, Status status, Entity<MyTestrailPlan> integTestrailPlanInfo)
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

    public Entity<MyProductQA> getMyProductQAInfo()
    {
        return integProductQAInfo;
    }

    public Status getStatus()
    {
        return status;
    }

    public String getName() {
        return String.format("MyBuildQA for %s", getBuildQAInfo().getData().getName());
    }
    
    public Entity<MyTestrailPlan> getMyTestrailPlanInfo()
    {
        return integTestrailPlanInfo;
    }
    
    public MyBuildQA updateTestrailPlanInfo(Entity<MyTestrailPlan> integTestrailPlanInfo)
    {
        return new MyBuildQA(getBuildQAInfo(), getMyProductQAInfo(), Status.Testing, integTestrailPlanInfo);
    }
    
    public MyBuildQA prepare()
    {
        return new MyBuildQA(getBuildQAInfo(), getMyProductQAInfo(), Status.Preparing, null);
    }
    
    public MyBuildQA end()
    {
        return new MyBuildQA(getBuildQAInfo(), getMyProductQAInfo(), Status.End, getMyTestrailPlanInfo());
    }
    
    public MyBuildQA testing()
    {
        return new MyBuildQA(getBuildQAInfo(), getMyProductQAInfo(), Status.Testing, getMyTestrailPlanInfo());
    }
}
