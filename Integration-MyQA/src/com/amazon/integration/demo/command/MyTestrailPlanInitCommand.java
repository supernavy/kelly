package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanInitCommand extends AbsCommand<Entity<MyTestrailPlan>>
{
    String integTestrailPlanId;

    public MyTestrailPlanInitCommand(String integTestrailPlanId)
    {
        this.integTestrailPlanId = integTestrailPlanId;
    }

    public String getMyTestrailPlanId()
    {
        return integTestrailPlanId;
    }
}
