package com.amazon.extension.testrail.domain.event;

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
