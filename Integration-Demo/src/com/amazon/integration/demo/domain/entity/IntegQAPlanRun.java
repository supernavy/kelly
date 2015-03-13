package com.amazon.integration.demo.domain.entity;

public class IntegQAPlanRun
{
    String qaPlanRunId;
    Long testrailTestPlanId;
    public IntegQAPlanRun(String qaPlanRunId, Long testrailTestPlanId)
    {
        this.qaPlanRunId = qaPlanRunId;
        this.testrailTestPlanId = testrailTestPlanId;
    }
    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }
    public Long getTestrailTestPlanId()
    {
        return testrailTestPlanId;
    }
}
