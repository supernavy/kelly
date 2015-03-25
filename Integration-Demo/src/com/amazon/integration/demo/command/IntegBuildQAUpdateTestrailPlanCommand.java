package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQAUpdateTestrailPlanCommand extends AbsCommand<Entity<IntegBuildQA>>
{
    String integBuildQAId;
    String integTestrailPlanId;
    public IntegBuildQAUpdateTestrailPlanCommand(String integBuildQAId, String integTestrailPlanId)
    {
        this.integBuildQAId = integBuildQAId;
        this.integTestrailPlanId = integTestrailPlanId;
    }
    public String getIntegBuildQAId()
    {
        return integBuildQAId;
    }
    public String getIntegTestrailPlanId()
    {
        return integTestrailPlanId;
    }
}
