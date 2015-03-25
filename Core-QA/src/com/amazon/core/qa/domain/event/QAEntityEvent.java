package com.amazon.core.qa.domain.event;

import com.amazon.infra.eventbus.EntityEvent;

public class QAEntityEvent implements EntityEvent<String>
{
    String entityId;

    public QAEntityEvent(String entityId)
    {
        this.entityId = entityId;
    }
    
    @Override
    public String getEntityId()
    {
        return entityId;
    }
}
