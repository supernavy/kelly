package com.amazon.integration.demo.event;

public class IntegQAProjectCreatedEvent implements IntegEvent
{
    String integQAProjectId;

    public IntegQAProjectCreatedEvent(String integQAProjectId)
    {
        this.integQAProjectId = integQAProjectId;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    
}
