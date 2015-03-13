package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestPlanCommand extends AbsCommand<JSONObject>
{
    Long planId;

    public GetTestPlanCommand(Long planId)
    {
        this.planId = planId;
    }
    public Long getPlanId()
    {
        return planId;
    }
}
