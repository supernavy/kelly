package com.amazon.integration.demo.domain.entity;

public class IntegQATestSuite
{
    String qaTestSuiteId;
    Long testrailProjectId;
    Long testrailTestSuiteId;
    public IntegQATestSuite(String qaTestSuiteId, Long testrailProjectId, Long testrailTestSuiteId)
    {
        this.qaTestSuiteId = qaTestSuiteId;
        this.testrailProjectId = testrailProjectId;
        this.testrailTestSuiteId = testrailTestSuiteId;
    }
    public String getQaTestSuiteId()
    {
        return qaTestSuiteId;
    }
    public Long getTestrailProjectId()
    {
        return testrailProjectId;
    }
    public Long getTestrailTestSuiteId()
    {
        return testrailTestSuiteId;
    }
}
