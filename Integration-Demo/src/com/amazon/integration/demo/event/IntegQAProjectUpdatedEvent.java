package com.amazon.integration.demo.event;

public class IntegQAProjectUpdatedEvent implements IntegEvent
{
    String integQAProjectId;
    String qaProjectId;

    public IntegQAProjectUpdatedEvent(String integQAProjectId, String qaProjectId)
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
