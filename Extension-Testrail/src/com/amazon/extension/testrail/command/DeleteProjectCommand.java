package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class DeleteProjectCommand extends AbsCommand<JSONObject>
{
    Long projectId;
    
    public DeleteProjectCommand(Long projectId)
    {
        this.projectId = projectId;
    }
    
    public Long getProjectId()
    {
        return projectId;
    }
}
