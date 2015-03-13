package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class AddPlanToIntegQAProjectCommand extends AbsCommand<Entity<IntegQAProject>>
{
    String qaProjectId;
    String planName;
    Long testrailSuiteId;
    public AddPlanToIntegQAProjectCommand(String qaProjectId, String planName, Long testrailSuiteId)
    {
        this.qaProjectId = qaProjectId;
        this.planName = planName;
        this.testrailSuiteId = testrailSuiteId;
    }
    public String getQaProjectId()
    {
        return qaProjectId;
    }
    public String getPlanName()
    {
        return planName;
    }
    public Long getTestrailSuiteId()
    {
        return testrailSuiteId;
    }
}
