package com.amazon.integration.demo.domain.event;


public class IntegTestrailPlanReadyEvent extends IntegEntityEvent
{

    public IntegTestrailPlanReadyEvent(String entityId)
    {
        super(entityId);
    }

}
