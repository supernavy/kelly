package com.amazon.integration.demo.domain.event;


public class MyTestrailProjectNewEvent extends MyEntityEvent
{

    public MyTestrailProjectNewEvent(String entityId)
    {
        super(entityId);
    }

}
