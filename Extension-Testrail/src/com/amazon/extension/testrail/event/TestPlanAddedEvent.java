package com.amazon.extension.testrail.event;

public class TestPlanAddedEvent implements TestrailEvent
{
    Long projectId;
    Long testPlanId;
    
    public TestPlanAddedEvent(Long projectId, Long testPlanId)
    {
        this.projectId = projectId;
        this.testPlanId = testPlanId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
    public Long getTestPlanId()
    {
        return testPlanId;
    }
}
