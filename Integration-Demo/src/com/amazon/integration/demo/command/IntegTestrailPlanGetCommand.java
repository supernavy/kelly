package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanGetCommand extends AbsCommand<Entity<IntegTestrailPlan>>
{
    String integTestrailPlanId;

    public IntegTestrailPlanGetCommand(String integTestrailPlanId)
    {
        this.integTestrailPlanId = integTestrailPlanId;
    }

    public String getIntegTestrailPlanId()
    {
        return integTestrailPlanId;
    }
}
