package com.amazon.integration.demo.domain.entity;

import com.amazon.infra.domain.Entity;


public class MyTestrailProject
{
    public enum Status {New, Ready, End};
    
    Entity<MyProductQA> integProductQAInfo;
    Long testrailProjectId;
    Long testrailSuiteId;
    Status status;
    
    public MyTestrailProject(Entity<MyProductQA> integProductQAInfo)
    {
        this(integProductQAInfo, null, null, Status.New);
    }

    private MyTestrailProject(Entity<MyProductQA> integProductQAInfo, Long testrailProjectId, Long testrailSuiteId, Status status)
    {
        this.integProductQAInfo = integProductQAInfo;
        this.testrailProjectId = testrailProjectId;
        this.testrailSuiteId = testrailSuiteId;
        this.status = status;
    }

    public Entity<MyProductQA> getMyProductQAInfo()
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
    
    public MyTestrailProject updateTestrailInfo(Long testrailProjectId, Long testrailSuiteId)
    {
        return new MyTestrailProject(getMyProductQAInfo(), testrailProjectId, testrailSuiteId, Status.Ready);
    }
    
    public MyTestrailProject end()
    {
        return new MyTestrailProject(getMyProductQAInfo(), getTestrailProjectId(), getTestrailSuiteId(), Status.End);
    }
}
