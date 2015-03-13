package com.amazon.integration.demo.domain.entity;

import java.util.Map;
import java.util.TreeMap;

public class IntegQAProject
{
    String qaProjectId;
    Long testrailProjectId;
    Map<String, Long> planSuiteIds = new TreeMap<String, Long>();
    
    public IntegQAProject(String qaProjectId, Long testrailProjectId, Map<String, Long> planSuiteIds)
    {
        this.qaProjectId = qaProjectId;
        this.testrailProjectId = testrailProjectId;
        if(planSuiteIds!=null)
        {
            this.planSuiteIds.putAll(planSuiteIds);
        }
    }
    
    public Map<String, Long> getPlanSuiteIds()
    {
        return new TreeMap<String, Long>(planSuiteIds);
    }
    
    public String getQaProjectId()
    {
        return qaProjectId;
    }
    public Long getTestrailProjectId()
    {
        return testrailProjectId;
    }
}
