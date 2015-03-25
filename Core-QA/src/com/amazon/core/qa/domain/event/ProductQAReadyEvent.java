package com.amazon.core.qa.domain.event;

public class ProductQAReadyEvent extends QAEntityEvent
{

    public ProductQAReadyEvent(String entityId)
    {
        super(entityId);
    }

}
