package com.amazon.extension.testrail.event;

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
