package com.amazon.integration.demo.event;

public class IntegQAProjectUpdatedEvent implements IntegEvent
{
    String integQAProjectId;

    public IntegQAProjectUpdatedEvent(String integQAProjectId)
    {
        this.integQAProjectId = integQAProjectId;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    
}
