package com.amazon.core.rm.domain.event;

import com.amazon.infra.eventbus.EntityEvent;

public class BuildNewEvent implements EntityEvent<String>
{
    String id;
    public BuildNewEvent(String id)
    {
        this.id = id;
    }
    @Override
    public String getEntityId()
    {
        return id;
    }
}
