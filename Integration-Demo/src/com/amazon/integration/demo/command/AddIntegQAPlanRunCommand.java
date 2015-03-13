package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;

public class AddIntegQAPlanRunCommand extends AbsCommand<Entity<IntegQAPlanRun>>
{
    String qaPlanRunId;
    Long testrailTestPlanId;
    public AddIntegQAPlanRunCommand(String qaPlanRunId, Long testrailTestPlanId)
    {
        this.qaPlanRunId = qaPlanRunId;
        this.testrailTestPlanId = testrailTestPlanId;
    }
    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }
    public Long getTestrailTestPlanId()
    {
        return testrailTestPlanId;
    }
}
