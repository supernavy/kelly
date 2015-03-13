package com.amazon.integration.demo.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class UpdateTestrailProjectCommand extends AbsCommand<JSONObject>
{
    String qaProjectId;

    public UpdateTestrailProjectCommand(String qaProjectId)
    {
        this.qaProjectId = qaProjectId;
    }
    public String getQaProjectId()
    {
        return qaProjectId;
    }
}
