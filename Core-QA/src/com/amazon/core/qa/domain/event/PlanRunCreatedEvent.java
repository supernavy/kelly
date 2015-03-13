package com.amazon.core.qa.domain.event;

import com.amazon.core.qa.domain.entity.PlanRun;

public class PlanRunCreatedEvent implements QAEvent
{
    String planRunId;
    PlanRun planRun;
    
    public PlanRunCreatedEvent(String planRunId, PlanRun planRun)
    {
        this.planRunId = planRunId;
        this.planRun = planRun;
    }
    
    public PlanRun getPlanRun()
    {
        return planRun;
    }
    
    public String getPlanRunId()
    {
        return planRunId;
    }
}
