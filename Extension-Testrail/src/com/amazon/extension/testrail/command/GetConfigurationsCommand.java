package com.amazon.extension.testrail.command;

import org.json.simple.JSONArray;
import com.amazon.infra.commandbus.AbsCommand;

public class GetConfigurationsCommand extends AbsCommand<JSONArray>
{
    Long projectId;

    public GetConfigurationsCommand(Long projectId)
    {
        this.projectId = projectId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
}
