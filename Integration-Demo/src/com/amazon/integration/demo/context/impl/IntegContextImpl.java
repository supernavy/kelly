package com.amazon.integration.demo.context.impl;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;
import com.amazon.integration.demo.domain.entity.IntegQAProject;
import com.amazon.integration.demo.domain.entity.IntegQATestCase;
import com.amazon.integration.demo.domain.entity.IntegQATestSuite;
import com.amazon.integration.demo.event.IntegQAPlanRunAddedEvent;
import com.amazon.integration.demo.event.IntegQAPlanRunUpdatedEvent;
import com.amazon.integration.demo.event.IntegQAProjectCreatedEvent;
import com.amazon.integration.demo.event.IntegQAProjectPlanAddedEvent;
import com.amazon.integration.demo.event.IntegQAProjectPlanDeletedEvent;
import com.amazon.integration.demo.event.IntegQAProjectUpdatedEvent;

public class IntegContextImpl extends AbsAppContextImpl implements IntegContext
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    
    Repository<IntegQAProject> integQAProjectRepo;
    Repository<IntegQAPlanRun> integQAPlanRunRepo;
    Repository<IntegQATestSuite> integQATestSuiteRepo;
    Repository<IntegQATestCase> integQATestCaseRepo;

    public IntegContextImpl(EventBus eventBus, Repository<IntegQAProject> integQAProjectRepo, Repository<IntegQAPlanRun> integQAPlanRunRepo, Repository<IntegQATestSuite> integQATestSuiteRepo, Repository<IntegQATestCase> integQATestCaseRepo)
    {
        super(eventBus);
        this.integQAProjectRepo = integQAProjectRepo;
        this.integQAPlanRunRepo = integQAPlanRunRepo;
        this.integQATestSuiteRepo = integQATestSuiteRepo;
        this.integQATestCaseRepo = integQATestCaseRepo;
    }

    @Override
    public Entity<IntegQAPlanRun> createIntegQAPlanRun(String qaPlanRunId, Long testrailTestPlanId) throws AppContextException
    {
        try {
            Entity<IntegQAPlanRun> integQAPlanRunEntity = integQAPlanRunRepo.createEntity(new IntegQAPlanRun(qaPlanRunId, testrailTestPlanId));
            publishEvent(new IntegQAPlanRunAddedEvent(integQAPlanRunEntity.getId()));
            return integQAPlanRunEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegQAPlanRun> loadIntegQAPlanRun(final String qaPlanRunId) throws AppContextException
    {
        try {
            Set<Entity<IntegQAPlanRun>> results = integQAPlanRunRepo.find(new EntitySpec<IntegQAPlanRun>(){

                @Override
                public boolean matches(Entity<IntegQAPlanRun> entity)
                {
                    if(entity.getData().getQaPlanRunId().equals(qaPlanRunId))
                        return true;
                    return false;
                }});
            if(results.size() == 0)
                throw new AppContextException(String.format("Not found [%s] with qaPlanRunId[%s]",IntegQAPlanRun.class, qaPlanRunId));
            if(results.size()>1)
                throw new AppContextException(String.format("Found multiple [%s] with qaPlanRunId[%s]", IntegQAPlanRun.class, qaPlanRunId));
            return results.iterator().next();
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegQAProject> createIntegQAProject(String qaProjectId, Long testrailProjectId) throws AppContextException
    {
        logger.fine(String.format("qaProjectId[%s], testrailProjectId[%s]",qaProjectId,testrailProjectId));
        try {
            Entity<IntegQAProject> integQAProjectEntity = integQAProjectRepo.createEntity(new IntegQAProject(qaProjectId, testrailProjectId, null));
            publishEvent(new IntegQAProjectCreatedEvent(integQAProjectEntity.getId()));
            logger.fine(String.format("created entity[%s]",integQAProjectEntity));
            return integQAProjectEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegQAProject> loadIntegQAProject(final String qaProjectId) throws AppContextException
    {
        try {
            Set<Entity<IntegQAProject>> results = integQAProjectRepo.find(new EntitySpec<IntegQAProject>(){

                @Override
                public boolean matches(Entity<IntegQAProject> entity)
                {
                    if(entity.getData().getQaProjectId().equals(qaProjectId))
                        return true;
                    return false;
                }});
            if(results.size() == 0)
                throw new AppContextException(String.format("Not found [%s] with qaProjectId[%s]", IntegQAProject.class, qaProjectId));
            if(results.size()>1)
                throw new AppContextException(String.format("Found multiple [%s] with qaProjectId[%s]", IntegQAProject.class, qaProjectId));
            return results.iterator().next();
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegQATestSuite> createIntegQATestSuite(String qaTestSuiteId, Long testrailProjectId, Long testrailSuiteId) throws AppContextException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Entity<IntegQATestSuite> loadIntegQATestSuite(String qaTestSuiteId) throws AppContextException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Entity<IntegQATestCase> createIntegQATestCase(String qaPlanRunId, Long testrailProjectId) throws AppContextException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Entity<IntegQATestCase> loadIntegQATestCase(String qaPlanRunId) throws AppContextException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Entity<IntegQAProject> updateIntegQAProject(String qaProjectId, IntegQAProject integQAProject) throws AppContextException
    {
        try {
            Entity<IntegQAProject> integQAProjectEntity = loadIntegQAProject(qaProjectId);
            integQAProjectEntity =  integQAProjectRepo.updateEntity(integQAProjectEntity.getId(), integQAProject);
            publishEvent(new IntegQAProjectUpdatedEvent(integQAProjectEntity.getId()));
            return integQAProjectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegQAPlanRun> updateIntegQAPlanRun(String qaPlanRunId, IntegQAPlanRun integQAPlanRun) throws AppContextException
    {
        try {
            Entity<IntegQAPlanRun> integQAPlanRunEntity = loadIntegQAPlanRun(qaPlanRunId);
            integQAPlanRunEntity =  integQAPlanRunRepo.updateEntity(integQAPlanRunEntity.getId(), integQAPlanRun);
            publishEvent(new IntegQAPlanRunUpdatedEvent(integQAPlanRunEntity.getId()));
            return integQAPlanRunEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegQAProject> addPlanToIntegQAProject(String qaProjectId, String planName, Long testrailSuiteId) throws AppContextException
    {
        Entity<IntegQAProject> integQAProjectEntity = loadIntegQAProject(qaProjectId);
        Map<String, Long> integQAProjectPlans = integQAProjectEntity.getData().getPlanSuiteIds();
        integQAProjectPlans.put(planName, testrailSuiteId);
        IntegQAProject integQAProject = new IntegQAProject(qaProjectId, integQAProjectEntity.getData().getTestrailProjectId(), integQAProjectPlans);
        Entity<IntegQAProject> updatedIntegQAProjectEntity = updateIntegQAProject(qaProjectId, integQAProject);
        publishEvent(new IntegQAProjectPlanAddedEvent(updatedIntegQAProjectEntity.getId(), planName));
        return updatedIntegQAProjectEntity;
    }

    @Override
    public Entity<IntegQAProject> deletePlanFromIntegQAProject(String qaProjectId, String planName) throws AppContextException
    {
        Entity<IntegQAProject> integQAProjectEntity = loadIntegQAProject(qaProjectId);
        Map<String, Long> integQAProjectPlans = integQAProjectEntity.getData().getPlanSuiteIds();
        integQAProjectPlans.remove(planName);
        IntegQAProject integQAProject = new IntegQAProject(qaProjectId, integQAProjectEntity.getData().getTestrailProjectId(), integQAProjectPlans);
        Entity<IntegQAProject> updatedIntegQAProjectEntity = updateIntegQAProject(qaProjectId, integQAProject);
        publishEvent(new IntegQAProjectPlanDeletedEvent(updatedIntegQAProjectEntity.getId(), planName));
        return updatedIntegQAProjectEntity;
    }
}
