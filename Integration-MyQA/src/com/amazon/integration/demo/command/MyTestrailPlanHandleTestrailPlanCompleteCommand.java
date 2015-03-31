package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;

public class MyTestrailPlanHandleTestrailPlanCompleteCommand extends AbsCommand<Void>
{
    Long testrailPlanId;

    public MyTestrailPlanHandleTestrailPlanCompleteCommand(Long testrailPlanId)
    {
        this.testrailPlanId = testrailPlanId;
    }

    public Long getTestrailPlanId()
    {
        return testrailPlanId;
    }
}
