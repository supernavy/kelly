package com.amazon.integration.demo.domain.event;


public class MyTestrailProjectReadyEvent extends MyEntityEvent
{

    public MyTestrailProjectReadyEvent(String entityId)
    {
        super(entityId);
    }

}
