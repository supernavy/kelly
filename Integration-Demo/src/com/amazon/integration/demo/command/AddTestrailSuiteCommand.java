package com.amazon.integration.demo.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddTestrailSuiteCommand extends AbsCommand<JSONObject>
{
    String qaProjectId;
    String planName;
    public AddTestrailSuiteCommand(String qaProjectId, String planName)
    {
        this.qaProjectId = qaProjectId;
        this.planName = planName;
    }
    public String getQaProjectId()
    {
        return qaProjectId;
    }
    public String getPlanName()
    {
        return planName;
    }
}
