package com.amazon.integration.demo.event;

public class IntegQAProjectPlanUpdatedEvent implements IntegEvent
{
    String integQAProjectId;
    String planName;
    Long testrailSuiteId;
    
    public IntegQAProjectPlanUpdatedEvent(String integQAProjectId,String planName, Long testrailSuiteId)
    {
        this.integQAProjectId = integQAProjectId;
        this.planName = planName;
        this.testrailSuiteId = testrailSuiteId;
    }

    public String getIntegQAProjectId()
    {
        return integQAProjectId;
    }
    
    public String getPlanName()
    {
        return planName;
    }
    
    public Long getTestrailSuiteId()
    {
        return testrailSuiteId;
    }
}
