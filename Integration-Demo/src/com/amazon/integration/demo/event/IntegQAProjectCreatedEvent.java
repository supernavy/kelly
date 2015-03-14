package com.amazon.integration.demo.event;

public class IntegQAProjectCreatedEvent implements IntegEvent
{
    String integQAProjectId;
    String qaProjectId;

    public IntegQAProjectCreatedEvent(String integQAProjectId, String qaProjectId)
    {
        this.integQAProjectId = integQAProjectId;
        this.qaProjectId = qaProjectId;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    public String getQaProjectId()
    {
        return qaProjectId;
    }
}
