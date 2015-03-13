package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class CreatePlanRunCommand extends AbsCommand<Entity<PlanRun>>
{
    String projectId;
    String planName;
    String buildId;
    public CreatePlanRunCommand(String projectId, String planName, String buildId)
    {
        this.projectId = projectId;
        this.planName = planName;
        this.buildId = buildId;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public String getPlanName()
    {
        return planName;
    }
    
    public String getBuildId()
    {
        return buildId;
    } 
}
