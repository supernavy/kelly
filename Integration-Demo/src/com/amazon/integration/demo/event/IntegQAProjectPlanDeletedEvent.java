package com.amazon.integration.demo.event;

public class IntegQAProjectPlanDeletedEvent implements IntegEvent
{
    String integQAProjectId;
    String planName;
    
    public IntegQAProjectPlanDeletedEvent(String integQAProjectId,String planName)
    {
        this.integQAProjectId = integQAProjectId;
        this.planName = planName;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    
    public String getPlanName()
    {
        return planName;
    }
}
