package com.amazon.integration.demo.domain.event;


public class ExternalSignoffAssignedEvent extends IntegEntityEvent
{

    public ExternalSignoffAssignedEvent(String entityId)
    {
        super(entityId);
    }

}
