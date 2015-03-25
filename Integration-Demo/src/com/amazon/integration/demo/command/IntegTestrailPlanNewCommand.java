package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;

public class IntegTestrailPlanNewCommand extends AbsCommand<Entity<IntegTestrailPlan>>
{
    String integBuildQAId;

    public IntegTestrailPlanNewCommand(String integBuildQAId)
    {
        this.integBuildQAId = integBuildQAId;
    }

    public String getIntegBuildQAId()
    {
        return integBuildQAId;
    }
}
