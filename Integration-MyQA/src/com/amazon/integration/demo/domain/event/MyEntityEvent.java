package com.amazon.integration.demo.domain.event;

import com.amazon.infra.eventbus.EntityEvent;

public class MyEntityEvent implements EntityEvent<String>
{
    String id;
        
    public MyEntityEvent(String id)
    {
        this.id = id;
    }

    @Override
    public String getEntityId()
    {
        return id;
    }

}
