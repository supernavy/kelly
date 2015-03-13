package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class AddIntegQAProjectCommand extends AbsCommand<Entity<IntegQAProject>>
{
    String qaProjectId;
    Long testrailProjectId;

    public AddIntegQAProjectCommand(String qaProjectId, Long testrailProjectId)
    {
        this.qaProjectId = qaProjectId;
        this.testrailProjectId = testrailProjectId;
    }

    public String getQaProjectId()
    {
        return qaProjectId;
    }
    
    public Long getTestrailProjectId()
    {
        return testrailProjectId;
    }
}
