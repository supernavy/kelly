package com.amazon.integration.demo.domain.event;

import com.amazon.infra.eventbus.EntityEvent;

public class IntegEntityEvent implements EntityEvent<String>
{
    String id;
        
    public IntegEntityEvent(String id)
    {
        this.id = id;
    }

    @Override
    public String getEntityId()
    {
        return id;
    }

}
