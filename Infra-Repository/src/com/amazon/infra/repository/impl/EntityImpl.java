package com.amazon.infra.repository.impl;

import java.io.Serializable;
import com.amazon.infra.domain.Entity;

public class EntityImpl<X> implements Entity<X>, Serializable
{
    /**
     * TODO
     */
    private static final long serialVersionUID = 9105628882810579248L;
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