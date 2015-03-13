package com.amazon.core.qa.domain.entity;

import com.amazon.core.qa.domain.vo.planrun.PlanRunResult;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.domain.Entity;

public class PlanRun
{
    Entity<Project> projectInfo;
    Plan plan;
    
    Entity<Build> buildInfo;
    
    PlanRunResult planRunResult;
    
    public PlanRun(Entity<Project> projectInfo, Plan plan, Entity<Build> buildInfo)
    {
        this.projectInfo = projectInfo;
        this.plan = plan;
        this.buildInfo = buildInfo;
    }
    
    public Entity<Project> getProjectInfo()
    {
        return projectInfo;
    }
    
    public Plan getPlan()
    {
        return plan;
    }
    
    public Entity<Build> getBuildInfo()
    {
        return buildInfo;
    }
}
