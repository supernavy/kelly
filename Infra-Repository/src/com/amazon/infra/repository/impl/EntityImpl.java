package com.amazon.infra.repository.impl;

import com.amazon.infra.domain.Entity;

public class EntityImpl<X> implements Entity<X>
{
    String id;
    X body;

    public EntityImpl(String id, X body)
    {
        this.id = id;
        this.body = body;
    }

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public X getData()
    {
        return body;
    }

    public void setBody(X body) {
        this.body = body;
    }
    
    @Override
    public String toString()
    {
        return String.format("id=%s,data=%s", getId(), getData());
    }
}