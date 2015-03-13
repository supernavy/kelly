package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class UpdateIntegQAProjectCommand extends AbsCommand<Entity<IntegQAProject>>
{
    String qaProjectId;
    IntegQAProject integQAProject;

    public UpdateIntegQAProjectCommand(String qaProjectId, IntegQAProject integQAProject)
    {
        this.qaProjectId = qaProjectId;
        this.integQAProject = integQAProject;
    }

    public String getQaProjectId()
    {
        return qaProjectId;
    }
    
    public IntegQAProject getIntegQAProject()
    {
        return integQAProject;
    }
}
