package com.amazon.integration.demo.domain.event;


public class ExternalSignoffEndEvent extends MyEntityEvent
{

    public ExternalSignoffEndEvent(String entityId)
    {
        super(entityId);
    }

}
