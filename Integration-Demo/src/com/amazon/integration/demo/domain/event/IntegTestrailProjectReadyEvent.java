package com.amazon.integration.demo.domain.event;


public class IntegTestrailProjectReadyEvent extends IntegEntityEvent
{

    public IntegTestrailProjectReadyEvent(String entityId)
    {
        super(entityId);
    }

}
