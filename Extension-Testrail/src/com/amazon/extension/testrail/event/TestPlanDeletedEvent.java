package com.amazon.extension.testrail.event;

public class TestPlanDeletedEvent implements TestrailEvent
{
    Long projectId;
    Long testPlanId;
    
    public TestPlanDeletedEvent(Long projectId, Long testPlanId)
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
