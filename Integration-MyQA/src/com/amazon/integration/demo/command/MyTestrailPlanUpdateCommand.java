package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanUpdateCommand extends AbsCommand<Entity<MyTestrailPlan>>
{
    String integTestrailPlanId;
    MyTestrailPlan myTestrailPlan;
    public MyTestrailPlanUpdateCommand(String integTestrailPlanId, MyTestrailPlan myTestrailPlan)
    {
        this.integTestrailPlanId = integTestrailPlanId;
        this.myTestrailPlan = myTestrailPlan;
    }

    public String getMyTestrailPlanId()
    {
        return integTestrailPlanId;
    }
    
    public MyTestrailPlan getMyTestrailPlan()
    {
        return myTestrailPlan;
    }
}
