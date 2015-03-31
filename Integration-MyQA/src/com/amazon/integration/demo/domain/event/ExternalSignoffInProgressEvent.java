package com.amazon.integration.demo.domain.event;


public class ExternalSignoffInProgressEvent extends MyEntityEvent
{

    public ExternalSignoffInProgressEvent(String entityId)
    {
        super(entityId);
    }

}
