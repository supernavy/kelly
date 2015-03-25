package com.amazon.extension.testrail.domain.event;

public class TestSuiteDeletedEvent implements TestrailEvent
{
    Long suiteId;
    
    public TestSuiteDeletedEvent(Long suiteId)
    {
        this.suiteId = suiteId;
    }
    public Long getSuiteId()
    {
        return suiteId;
    }
}
