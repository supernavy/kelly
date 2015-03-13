package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class GetIntegQAProjectCommand extends AbsCommand<Entity<IntegQAProject>>
{
    String qaProjectId;

    public GetIntegQAProjectCommand(String qaProjectId)
    {
        this.qaProjectId = qaProjectId;
    }

    public String getQaProjectId()
    {
        return qaProjectId;
    }
}
