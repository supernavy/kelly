package com.amazon.integration.demo.domain.event;


public class ExternalSignoffInProgressEvent extends IntegEntityEvent
{

    public ExternalSignoffInProgressEvent(String entityId)
    {
        super(entityId);
    }

}
