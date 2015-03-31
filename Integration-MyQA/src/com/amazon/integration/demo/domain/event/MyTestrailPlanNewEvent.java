package com.amazon.integration.demo.domain.event;

public class MyTestrailPlanNewEvent extends MyEntityEvent
{

    public MyTestrailPlanNewEvent(String entityId)
    {
        super(entityId);
    }

}
