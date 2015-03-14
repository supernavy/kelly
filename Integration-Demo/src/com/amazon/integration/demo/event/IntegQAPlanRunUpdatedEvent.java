package com.amazon.integration.demo.event;

public class IntegQAPlanRunUpdatedEvent implements IntegEvent
{
    String integQAPlanRunId;
    String qaPlanRunId;

    public IntegQAPlanRunUpdatedEvent(String integQAPlanRunId, String qaPlanRunId)
    {
        this.integQAPlanRunId = integQAPlanRunId;
        this.qaPlanRunId = qaPlanRunId;
    }

    public String getIntegQAProjectId()
    {
        return integQAPlanRunId;
    }
    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }
}
