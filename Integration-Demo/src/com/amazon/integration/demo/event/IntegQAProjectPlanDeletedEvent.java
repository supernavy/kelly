package com.amazon.integration.demo.event;

public class IntegQAProjectPlanDeletedEvent implements IntegEvent
{
    String integQAProjectId;
    String qaProjectId;
    String planName;
    
    public IntegQAProjectPlanDeletedEvent(String integQAProjectId,String qaProjectId,String planName)
    {
        this.integQAProjectId = integQAProjectId;
        this.qaProjectId = qaProjectId;
        this.planName = planName;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    public String getQaProjectId()
    {
        return qaProjectId;
    }
    public String getPlanName()
    {
        return planName;
    }
}
