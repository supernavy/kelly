package com.amazon.extension.testrail.event;

public class TestSuiteDeletedEvent implements TestrailEvent
{
    Long projectId;
    Long suiteId;
    
    public TestSuiteDeletedEvent(Long projectId, Long suiteId)
    {
        this.projectId = projectId;
        this.suiteId = suiteId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
    public Long getSuiteId()
    {
        return suiteId;
    }
}
