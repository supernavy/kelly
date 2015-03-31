package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAUpdateTestrailPlanCommand extends AbsCommand<Entity<MyBuildQA>>
{
    String integBuildQAId;
    String integTestrailPlanId;
    public MyBuildQAUpdateTestrailPlanCommand(String integBuildQAId, String integTestrailPlanId)
    {
        this.integBuildQAId = integBuildQAId;
        this.integTestrailPlanId = integTestrailPlanId;
    }
    public String getMyBuildQAId()
    {
        return integBuildQAId;
    }
    public String getMyTestrailPlanId()
    {
        return integTestrailPlanId;
    }
}
