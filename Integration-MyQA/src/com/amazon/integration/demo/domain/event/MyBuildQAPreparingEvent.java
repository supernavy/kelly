package com.amazon.integration.demo.domain.event;


public class MyBuildQAPreparingEvent extends MyEntityEvent
{

    public MyBuildQAPreparingEvent(String entityId)
    {
        super(entityId);
    }

}
