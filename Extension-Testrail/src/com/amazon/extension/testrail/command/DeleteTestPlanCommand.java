package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class DeleteTestPlanCommand extends AbsCommand<JSONObject>
{
    Long planId;
    
    public DeleteTestPlanCommand(Long planId)
    {
        this.planId = planId;
    }
    
    public Long getPlanId()
    {
        return planId;
    }
}
