package com.amazon.integration.demo.domain.event;


public class ExternalSignoffNewEvent extends IntegEntityEvent
{

    public ExternalSignoffNewEvent(String entityId)
    {
        super(entityId);
    }

}
