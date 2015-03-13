package com.amazon.core.qa.context.impl;

import java.util.logging.Logger;
import com.amazon.core.qa.context.PlanRunContext;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.event.PlanRunCreatedEvent;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.core.rm.command.GetBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class PlanRunContextImpl extends AbsAppContextImpl implements PlanRunContext
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    Repository<PlanRun> planRunRepository;
    ProjectContext projectContext;
    CommandBus releaseSystemCommandBus;

    public PlanRunContextImpl(EventBus eventBus, Repository<PlanRun> planRunRepository,ProjectContext projectContext,CommandBus releaseSystemCommandBus)
    {
        super(eventBus);
        this.planRunRepository = planRunRepository;
        this.projectContext = projectContext;
        this.releaseSystemCommandBus = releaseSystemCommandBus;
    }

    @Override
    public Entity<PlanRun> createTestPlanRun(String projectId, String planName, String releaseBuildId) throws AppContextException
    {
        try {
            Entity<Project> projectInfo = projectContext.load(projectId);
            Plan plan = projectInfo.getData().getPlans().get(planName);
            Entity<Build> releaseBuildInfo = releaseSystemCommandBus.submit(new GetBuildCommand(releaseBuildId)).getResult();
            PlanRun planRun = new PlanRun(projectInfo, plan, releaseBuildInfo);

            Entity<PlanRun> planRunEntity = planRunRepository.createEntity(planRun);
            PlanRunCreatedEvent event = new PlanRunCreatedEvent(planRunEntity.getId(), planRunEntity.getData());
            publishEvent(event);
            return planRunEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<PlanRun> load(String planRunId) throws AppContextException
    {
        try {
            Entity<PlanRun> planRunEntity = planRunRepository.load(planRunId);
            return planRunEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
}
