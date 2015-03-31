package com.amazon.integration.demo.domain.event;


public class ExternalSignoffNewEvent extends MyEntityEvent
{

    public ExternalSignoffNewEvent(String entityId)
    {
        super(entityId);
    }

}
