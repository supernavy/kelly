package com.amazon.extension.testrail.api;

import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class TestrailProjectBuilder
{
    TestrailRootContext testrailContext;
    JSONObject project;
    public TestrailProjectBuilder(TestrailRootContext testrailContext)
    {
        this.testrailContext = testrailContext;
        this.project = new JSONObject();
    }
     
    public TestrailProjectBuilder setName(String name)
    {
        project.put(TestrailAPI.Key.Name, name);
        return this;
    }
    
    public TestrailProjectBuilder setDescription(String description)
    {
        project.put(TestrailAPI.Key.Description, description);
        return this;
    }
    
    public JSONObject build()
    {
        return project;
    }
}
