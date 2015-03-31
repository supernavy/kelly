package com.amazon.integration.demo.domain.event;


public class MyTestrailPlanTestInProgressEvent extends MyEntityEvent
{

    public MyTestrailPlanTestInProgressEvent(String entityId)
    {
        super(entityId);
    }

}
