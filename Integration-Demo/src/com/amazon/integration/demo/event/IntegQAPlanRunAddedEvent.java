package com.amazon.integration.demo.event;

public class IntegQAPlanRunAddedEvent implements IntegEvent
{
    String integQAPlanRunId;
    String qaPlanRunId;

    public IntegQAPlanRunAddedEvent(String integQAPlanRunId, String qaPlanRunId)
    {
        this.integQAPlanRunId = integQAPlanRunId;
        this.qaPlanRunId = qaPlanRunId;
    }

    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }
    public String getIntegQAPlanRunId()
    {
        return integQAPlanRunId;
    }
}
