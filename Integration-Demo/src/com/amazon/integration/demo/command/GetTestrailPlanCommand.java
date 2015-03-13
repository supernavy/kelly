package com.amazon.integration.demo.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestrailPlanCommand extends AbsCommand<JSONObject>
{
    String qaPlanRunId;

    public GetTestrailPlanCommand(String qaPlanRunId)
    {
        this.qaPlanRunId = qaPlanRunId;
    }
    
    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }
}
