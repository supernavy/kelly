package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.Project;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class GetProjectCommand extends AbsCommand<Entity<Project>>
{
    String projectId;

    public GetProjectCommand(String projectId)
    {
        this.projectId = projectId;
    }
    public String getProjectId()
    {
        return projectId;
    }
}
