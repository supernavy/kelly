package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddTestPlanCommand extends AbsCommand<JSONObject>
{
    Long projectId;
    JSONObject postData;
    
    public AddTestPlanCommand(Long projectId, JSONObject postData)
    {
        this.projectId = projectId;
        this.postData = postData;
    }
    
    public JSONObject getPostData()
    {
        return postData;
    }
    
    public Long getProjectId()
    {
        return projectId;
    }
}
