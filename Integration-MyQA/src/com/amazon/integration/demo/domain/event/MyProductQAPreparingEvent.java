package com.amazon.integration.demo.domain.event;


public class MyProductQAPreparingEvent extends MyEntityEvent
{

    public MyProductQAPreparingEvent(String entityId)
    {
        super(entityId);
    }

}
