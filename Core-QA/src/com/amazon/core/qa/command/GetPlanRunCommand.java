package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class GetPlanRunCommand extends AbsCommand<Entity<PlanRun>>
{
    String planRunId;
    public GetPlanRunCommand(String planRunId)
    {
        this.planRunId = planRunId;
    }
    
    public String getPlanRunId()
    {
        return planRunId;
    }
}
