package com.amazon.integration.demo.domain.event;


public class MyProductQANewEvent extends MyEntityEvent
{

    public MyProductQANewEvent(String entityId)
    {
        super(entityId);
    }

}
