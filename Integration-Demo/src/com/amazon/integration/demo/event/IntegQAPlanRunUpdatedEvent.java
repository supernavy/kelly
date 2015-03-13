package com.amazon.integration.demo.event;

public class IntegQAPlanRunUpdatedEvent implements IntegEvent
{
    String integQAProjectId;

    public IntegQAPlanRunUpdatedEvent(String integQAProjectId)
    {
        this.integQAProjectId = integQAProjectId;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    
}
