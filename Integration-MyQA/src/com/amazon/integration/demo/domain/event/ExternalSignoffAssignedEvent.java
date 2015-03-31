package com.amazon.integration.demo.domain.event;


public class ExternalSignoffAssignedEvent extends MyEntityEvent
{

    public ExternalSignoffAssignedEvent(String entityId)
    {
        super(entityId);
    }

}
