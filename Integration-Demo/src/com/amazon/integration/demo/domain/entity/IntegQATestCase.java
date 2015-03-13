package com.amazon.integration.demo.domain.entity;

public class IntegQATestCase
{
    String qaTestCaseId;
    Long testrailTestCaseId;
    public IntegQATestCase(String qaTestCaseId, Long testrailTestCaseId)
    {
        this.qaTestCaseId = qaTestCaseId;
        this.testrailTestCaseId = testrailTestCaseId;
    }
    public String getQaTestCaseId()
    {
        return qaTestCaseId;
    }
    public Long getTestrailTestCaseId()
    {
        return testrailTestCaseId;
    }
}
