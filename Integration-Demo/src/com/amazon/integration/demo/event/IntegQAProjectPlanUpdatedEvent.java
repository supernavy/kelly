package com.amazon.integration.demo.event;

public class IntegQAProjectPlanUpdatedEvent implements IntegEvent
{
    String integQAProjectId;
    String qaProjectId;
    String planName;
    Long testrailSuiteId;
    
    public IntegQAProjectPlanUpdatedEvent(String integQAProjectId, String qaProjectId, String planName, Long testrailSuiteId)
    {
        this.integQAProjectId = integQAProjectId;
        this.qaProjectId = qaProjectId;
        this.planName = planName;
        this.testrailSuiteId = testrailSuiteId;
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
    
    public Long getTestrailSuiteId()
    {
        return testrailSuiteId;
    }
}
