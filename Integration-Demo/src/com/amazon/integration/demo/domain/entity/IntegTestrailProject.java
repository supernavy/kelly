package com.amazon.integration.demo.domain.entity;

import com.amazon.infra.domain.Entity;


public class IntegTestrailProject
{
    public enum Status {New, Ready, End};
    
    Entity<IntegProductQA> integProductQAInfo;
    Long testrailProjectId;
    Long testrailSuiteId;
    Status status;
    
    public IntegTestrailProject(Entity<IntegProductQA> integProductQAInfo)
    {
        this(integProductQAInfo, null, null, Status.New);
    }

    private IntegTestrailProject(Entity<IntegProductQA> integProductQAInfo, Long testrailProjectId, Long testrailSuiteId, Status status)
    {
        this.integProductQAInfo = integProductQAInfo;
        this.testrailProjectId = testrailProjectId;
        this.testrailSuiteId = testrailSuiteId;
        this.status = status;
    }

    public Entity<IntegProductQA> getIntegProductQAInfo()
    {
        return integProductQAInfo;
    }

    public Long getTestrailProjectId()
    {
        return testrailProjectId;
    }

    public Status getStatus()
    {
        return status;
    } 
    
    public Long getTestrailSuiteId()
    {
        return testrailSuiteId;
    }
    
    public IntegTestrailProject updateTestrailInfo(Long testrailProjectId, Long testrailSuiteId)
    {
        return new IntegTestrailProject(getIntegProductQAInfo(), testrailProjectId, testrailSuiteId, Status.Ready);
    }
    
    public IntegTestrailProject end()
    {
        return new IntegTestrailProject(getIntegProductQAInfo(), getTestrailProjectId(), getTestrailSuiteId(), Status.End);
    }
}
