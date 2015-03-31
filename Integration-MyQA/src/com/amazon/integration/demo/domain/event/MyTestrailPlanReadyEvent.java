package com.amazon.integration.demo.domain.event;


public class MyTestrailPlanReadyEvent extends MyEntityEvent
{

    public MyTestrailPlanReadyEvent(String entityId)
    {
        super(entityId);
    }

}
