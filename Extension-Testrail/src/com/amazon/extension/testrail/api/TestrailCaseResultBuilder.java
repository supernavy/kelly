package com.amazon.extension.testrail.api;

import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class TestrailCaseResultBuilder
{
    JSONObject data = new JSONObject();
    
    public TestrailCaseResultBuilder setStatusId(Long statusId)
    {
        data.put(TestrailAPI.Key.Status_Id, statusId);
        return this;
    }
    
    public TestrailCaseResultBuilder setComment(String comment)
    {
        data.put(TestrailAPI.Key.Comment, comment);
        return this;
    }

    public TestrailCaseResultBuilder setElapsed(String elapsed)
    {
        data.put(TestrailAPI.Key.Elapsed, elapsed);
        return this;
    }
    
    public TestrailCaseResultBuilder setVersion(String version)
    {
        data.put(TestrailAPI.Key.Version, version);
        return this;
    }
    
    public JSONObject build()
    {
        return data;
    }
}
