package com.amazon.integration.demo.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestrailProjectCommand extends AbsCommand<JSONObject>
{
    String qaProjectId;

    public GetTestrailProjectCommand(String qaProjectId)
    {
        this.qaProjectId = qaProjectId;
    }
    
    public String getQaProjectId()
    {
        return qaProjectId;
    }
}
