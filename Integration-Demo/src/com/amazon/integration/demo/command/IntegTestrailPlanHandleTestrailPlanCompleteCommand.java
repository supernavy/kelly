package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;

public class IntegTestrailPlanHandleTestrailPlanCompleteCommand extends AbsCommand<Void>
{
    Long testrailPlanId;

    public IntegTestrailPlanHandleTestrailPlanCompleteCommand(Long testrailPlanId)
    {
        this.testrailPlanId = testrailPlanId;
    }

    public Long getTestrailPlanId()
    {
        return testrailPlanId;
    }
}
