package com.amazon.extension.testrail.event;

public class TestPlanDeletedEvent implements TestrailEvent
{
    Long testPlanId;
    
    public TestPlanDeletedEvent(Long testPlanId)
    {
        this.testPlanId = testPlanId;
    }

    public Long getTestPlanId()
    {
        return testPlanId;
    }
}
