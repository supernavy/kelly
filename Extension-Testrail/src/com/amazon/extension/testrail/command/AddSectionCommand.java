package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddSectionCommand extends AbsCommand<JSONObject>
{
    Long projectId;
    JSONObject postData;
    
    public AddSectionCommand(Long projectId, JSONObject postData)
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
