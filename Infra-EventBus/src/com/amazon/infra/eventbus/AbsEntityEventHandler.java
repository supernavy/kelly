package com.amazon.infra.eventbus;

public abstract class AbsEntityEventHandler<T> implements EntityEvent<T>
{
    T entityId;
    
    public AbsEntityEventHandler(T entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public T getEntityId()
    {
        return entityId;
    }
}
