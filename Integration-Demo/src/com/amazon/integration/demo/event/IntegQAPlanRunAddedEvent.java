package com.amazon.integration.demo.event;

public class IntegQAPlanRunAddedEvent implements IntegEvent
{
    String integQAProjectId;

    public IntegQAPlanRunAddedEvent(String integQAProjectId)
    {
        this.integQAProjectId = integQAProjectId;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    
}
