package com.amazon.integration.demo.domain.event;


public class ExternalSignoffEndEvent extends IntegEntityEvent
{

    public ExternalSignoffEndEvent(String entityId)
    {
        super(entityId);
    }

}
