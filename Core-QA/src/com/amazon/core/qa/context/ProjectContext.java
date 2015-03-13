package com.amazon.core.qa.context;

import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.infra.context.AppContext;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public interface ProjectContext extends AppContext
{
    public Entity<Project> createProject(String name, String productId) throws AppContextException;
    public Entity<Project> load(String id) throws AppContextException;
    public Entity<Project> addPlanToProject(String projectId, Plan plan) throws AppContextException;
}
