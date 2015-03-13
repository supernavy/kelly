package com.amazon.extension.testrail.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TestrailRootContext
{
    JSONArray testrailPriorities;
    
    public TestrailRootContext(JSONArray testrailPriorities)
    {
        this.testrailPriorities = testrailPriorities;
    }
    
    public JSONArray getTestrailPriorities()
    {
        return testrailPriorities;
    }
    
    public TestrailProjectContext newProjectInstance(JSONObject testrailProject, JSONArray testrailConfigs)
    {
        return new TestrailProjectContext(this, testrailProject, testrailConfigs);
    }   
    
    public TestrailProjectBuilder newProjectBuilder()
    {
        return new TestrailProjectBuilder(this);   
    }
}
