package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class UpdateTestSuiteCommand extends AbsCommand<JSONObject>
{
    Long suiteId;
    JSONObject postData;
    
    public UpdateTestSuiteCommand(Long suiteId, JSONObject postData)
    {
        this.suiteId = suiteId;
        this.postData = postData;
    }
    
    public JSONObject getPostData()
    {
        return postData;
    }
    
    public Long getSuiteId()
    {
        return suiteId;
    }
}
