package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanNewCommand extends AbsCommand<Entity<MyTestrailPlan>>
{
    String integBuildQAId;

    public MyTestrailPlanNewCommand(String integBuildQAId)
    {
        this.integBuildQAId = integBuildQAId;
    }

    public String getMyBuildQAId()
    {
        return integBuildQAId;
    }
}
