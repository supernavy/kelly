package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class AddPlanToProjectCommand extends AbsCommand<Entity<Project>>
{
    String projectId;
    Plan plan;
    public AddPlanToProjectCommand(String projectId, Plan plan)
    {
        this.projectId = projectId;
        this.plan = plan;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public Plan getPlan()
    {
        return plan;
    }
}
