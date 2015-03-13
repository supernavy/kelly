package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddProjectCommand extends AbsCommand<JSONObject>
{
    JSONObject postData;
    
    public AddProjectCommand(JSONObject postData)
    {
        this.postData = postData;
    }
    
    public JSONObject getPostData()
    {
        return postData;
    }
}
