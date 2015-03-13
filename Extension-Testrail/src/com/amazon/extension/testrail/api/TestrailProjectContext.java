package com.amazon.extension.testrail.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TestrailProjectContext
{
    TestrailRootContext testrailContext;
    JSONObject testrailProject; //testrail project level
    JSONArray testrailConfigs; //testrail project level

    public TestrailProjectContext(TestrailRootContext testrailContext, JSONObject testrailProject, JSONArray testrailConfigs)
    {
        this.testrailContext = testrailContext;
        this.testrailProject = testrailProject;
        this.testrailConfigs = testrailConfigs;
    }
    
    public TestrailRootContext getTestrailContext()
    {
        return testrailContext;
    }
    
    public JSONObject getTestrailProject()
    {
        return testrailProject;
    }

    public JSONArray getTestrailConfigs()
    {
        return testrailConfigs;
    }
    
    public TestrailTestPlanBuilder newTestPlanBuilder()
    {
        return new TestrailTestPlanBuilder(this);
    }
}