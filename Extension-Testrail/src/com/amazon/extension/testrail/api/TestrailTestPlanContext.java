package com.amazon.extension.testrail.api;

import org.json.simple.JSONObject;

public class TestrailTestPlanContext
{
    TestrailProjectContext projectContext;
    JSONObject plan;
    
    public TestrailTestPlanContext(TestrailProjectContext projectContext, JSONObject plan)
    {
        this.projectContext = projectContext;
        this.plan = plan;
    }
}
