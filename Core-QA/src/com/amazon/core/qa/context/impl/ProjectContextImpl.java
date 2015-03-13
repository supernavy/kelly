package com.amazon.core.qa.context.impl;

import java.util.logging.Logger;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.event.ProjectCreatedEvent;
import com.amazon.core.qa.domain.event.ProjectPlanAddedEvent;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class ProjectContextImpl extends AbsAppContextImpl implements ProjectContext
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    Repository<Project> projectEntityRepository;
    CommandBus pmSystemCommandBus;
    
    public ProjectContextImpl(EventBus eventBus, Repository<Project> projectEntityRepository, CommandBus pmSystemCommandBus)
    {
        super(eventBus);
        this.projectEntityRepository = projectEntityRepository;
        this.pmSystemCommandBus = pmSystemCommandBus;
    }

    @Override
    public Entity<Project> createProject(String name, String pmProductId) throws AppContextException
    {
        try {
            Entity<Product> productInfo = pmSystemCommandBus.submit(new GetProductCommand(pmProductId)).getResult();
            Project p = new Project(productInfo, name);
            Entity<Project> projectEntity = projectEntityRepository.createEntity(p);
            publishEvent(new ProjectCreatedEvent(projectEntity.getId()));
            return projectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<Project> load(String id) throws AppContextException
    {
        try {
            return projectEntityRepository.load(id);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<Project> addPlanToProject(String projectId, Plan plan) throws AppContextException
    {
        try {
            Entity<Project> projectEntity = projectEntityRepository.load(projectId);
            Project updatedProject = projectEntity.getData().addPlan(plan);
            projectEntity =  projectEntityRepository.updateEntity(projectEntity.getId(), updatedProject);
            publishEvent(new ProjectPlanAddedEvent(projectId, plan.getName()));
            return projectEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
}
