package com.amazon.integration.demo.domain.event;


public class MyBuildQANewEvent extends MyEntityEvent
{

    public MyBuildQANewEvent(String entityId)
    {
        super(entityId);
    }

}
