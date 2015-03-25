package com.amazon.integration.demo.context.impl;

import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.domain.vo.productqa.PlanEntry;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.api.TestrailProjectBuilder;
import com.amazon.extension.testrail.api.TestrailProjectContext;
import com.amazon.extension.testrail.api.TestrailRootContext;
import com.amazon.extension.testrail.api.TestrailTestPlanBuilder;
import com.amazon.extension.testrail.api.TestrailTestPlanBuilder.TestPlanEntryBuilder;
import com.amazon.extension.testrail.command.AddProjectCommand;
import com.amazon.extension.testrail.command.AddTestPlanCommand;
import com.amazon.extension.testrail.command.AddTestSuiteCommand;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.command.GetConfigurationsCommand;
import com.amazon.extension.testrail.command.GetPrioritiesCommand;
import com.amazon.extension.testrail.command.GetProjectCommand;
import com.amazon.extension.testrail.command.GetTestCasesCommand;
import com.amazon.extension.testrail.command.GetTestSuiteCommand;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;
import com.amazon.integration.demo.domain.entity.IntegProductQA;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanEndEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanNewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanReadyEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanTestInProgressEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectEndEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectNewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectReadyEvent;
import com.amazon.integration.demo.system.DemoSystem;

public class IntegTestrailContextImpl extends AbsAppContextImpl implements IntegTestrailContext
{
    Repository<IntegTestrailProject> integTestrailProjectRepo;
    Repository<IntegTestrailPlan> integTestrailPlanRepo;

    CommandBus testrailSystemCommandBus;
    CommandBus qaSystemCommandBus;
    
    IntegQAContext integQAContext;
    
    public IntegTestrailContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.integTestrailProjectRepo = appSystem.getRepository(DemoSystem.Repository_IntegTestrailProject);
        this.integTestrailPlanRepo = appSystem.getRepository(DemoSystem.Repository_IntegTestrailPlan);
        
        this.qaSystemCommandBus = appSystem.getDependency(DemoSystem.System_QA).getCommandBus();
        this.testrailSystemCommandBus = appSystem.getDependency(DemoSystem.System_Testrail).getCommandBus();
    }
    
    public void setIntegQAContext(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }
    
    @Override
    public Entity<IntegTestrailProject> newIntegTestrailProject(String integProductQAId) throws AppContextException
    {   
        try {
            Entity<IntegProductQA> integProductQAEntity = integQAContext.loadIntegProductQA(integProductQAId);
            IntegTestrailProject integTestrailProject = new IntegTestrailProject(integProductQAEntity);
            Entity<IntegTestrailProject> integTestrailProjectEntity = integTestrailProjectRepo.createEntity(integTestrailProject);
            publishEvent(new IntegTestrailProjectNewEvent(integTestrailProjectEntity.getId()));
            return integTestrailProjectEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
    
    private void publishIntegTestrailProjectEvent(IntegTestrailProject.Status oldStatus, IntegTestrailProject.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Ready: publishEvent(new IntegTestrailProjectReadyEvent(id));
                case End: publishEvent(new IntegTestrailProjectEndEvent(id));
                default:
                    break;
            }            
        }
    }

    @Override
    public Entity<IntegTestrailProject> loadIntegTestrailProject(String integTestrailProjectId) throws AppContextException
    {
        Entity<IntegTestrailProject> integTestrailProjectEntity;
        try {
            integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            return integTestrailProjectEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public Entity<IntegTestrailProject> initIntegTestrailProject(String integTestrailProjectId) throws AppContextException
    {        
        try {
            Entity<IntegTestrailProject> integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            IntegTestrailProject.Status oldStatus = integTestrailProjectEntity.getData().getStatus();
            
            JSONObject testrailProject = createTestrailProject(integTestrailProjectEntity);
            Long testrailProjectId = (Long) testrailProject.get(TestrailAPI.Key.Id);
            JSONObject testrailSuite = createTestrailSuite(integTestrailProjectEntity, testrailProjectId);
            Long testrailSuiteId = (Long) testrailSuite.get(TestrailAPI.Key.Id);
            IntegTestrailProject newIntegTestrailProject = integTestrailProjectEntity.getData().updateTestrailInfo(testrailProjectId, testrailSuiteId);
            integTestrailProjectEntity = integTestrailProjectRepo.updateEntity(integTestrailProjectEntity.getId(), newIntegTestrailProject);
            IntegTestrailProject.Status newStatus = integTestrailProjectEntity.getData().getStatus();
            
            publishIntegTestrailProjectEvent(oldStatus, newStatus, integTestrailProjectEntity.getId());
            
            return integTestrailProjectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private JSONObject createTestrailProject(Entity<IntegTestrailProject> integTestrailProjectEntity) throws CommandException, CommandBusException
    {       
        TestrailRootContext testrailInstance = new TestrailRootContext(loadTestrailPriorities());
        TestrailProjectBuilder testrailProjectBuilder = testrailInstance.newProjectBuilder();
        JSONObject data = testrailProjectBuilder.setName(mapTestrailProjectName(integTestrailProjectEntity)).setDescription(mapTestrailProjectDescription(integTestrailProjectEntity)).build();

        JSONObject testrailProject = testrailSystemCommandBus.submit(new AddProjectCommand(data)).getResult();
        return testrailProject;
    }
    
    @SuppressWarnings("unchecked")
    private JSONObject createTestrailSuite(Entity<IntegTestrailProject> integTestrailProjectEntity, Long testrailProjectId) throws CommandException, CommandBusException
    {       
        String suiteName = "Default Suite";
        JSONObject postData = new JSONObject();
        postData.put(TestrailAPI.Key.Name, suiteName);
        postData.put(TestrailAPI.Key.Description, "Created by automation");
        JSONObject testrailTestSuite = testrailSystemCommandBus.submit(new AddTestSuiteCommand(testrailProjectId, postData)).getResult();

        return testrailTestSuite;
    }
    
    private JSONArray loadTestrailPriorities() throws CommandException, CommandBusException
    {
        return testrailSystemCommandBus.submit(new GetPrioritiesCommand()).getResult();
    }
    
    private String mapTestrailProjectName(Entity<IntegTestrailProject> integTestrailProjectEntity)
    {
        return "QA for "+integTestrailProjectEntity.getData().getIntegProductQAInfo().getData().getProductQAInfo().getData().getProductInfo().getData().getName();
    }
    
    private String mapTestrailProjectDescription(Entity<IntegTestrailProject> integTestrailProjectEntity)
    {
        return "no description";
    }

    @Override
    public Entity<IntegTestrailProject> endIntegTestrailProject(String integTestrailProjectId) throws AppContextException
    {
        Entity<IntegTestrailProject> integTestrailProjectEntity;
        try {
            integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            IntegTestrailProject.Status oldStatus = integTestrailProjectEntity.getData().getStatus();
            testrailSystemCommandBus.submit(new DeleteProjectCommand(integTestrailProjectEntity.getData().getTestrailProjectId())).getResult();
            IntegTestrailProject newIntegTestrailProject = integTestrailProjectEntity.getData().end();
            integTestrailProjectEntity = integTestrailProjectRepo.updateEntity(integTestrailProjectId, newIntegTestrailProject);
            IntegTestrailProject.Status newStatus = integTestrailProjectEntity.getData().getStatus();
            publishIntegTestrailProjectEvent(oldStatus, newStatus, integTestrailProjectId);
            return integTestrailProjectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
        
    }

    @Override
    public Entity<IntegTestrailPlan> newIntegTestrailPlan(String integBuildQAId) throws AppContextException
    {
        try {
            Entity<IntegBuildQA> integBuildQAEntity = integQAContext.loadIntegBuildQA(integBuildQAId);
            IntegTestrailPlan integTestrailPlan = new IntegTestrailPlan(integBuildQAEntity);
            Entity<IntegTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.createEntity(integTestrailPlan);
            publishEvent(new IntegTestrailPlanNewEvent(integTestrailPlanEntity.getId()));
            return integTestrailPlanEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegTestrailPlan> initIntegTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            IntegTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            JSONObject testrailPlan = createTestrailPlan(integTestrailPlanEntity);
            Long testrailPlanId = (Long) testrailPlan.get(TestrailAPI.Key.Id);
            IntegTestrailPlan newIntegTestrailPlan = integTestrailPlanEntity.getData().updateTestrailPlanId(testrailPlanId);
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newIntegTestrailPlan);
            
            IntegTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishIntegTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
        
    }
    
    private void publishIntegTestrailPlanEvent(IntegTestrailPlan.Status oldStatus, IntegTestrailPlan.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Ready: publishEvent(new IntegTestrailPlanReadyEvent(id));
                case TestInProgress: publishEvent(new IntegTestrailPlanTestInProgressEvent(id));
                case End: publishEvent(new IntegTestrailPlanEndEvent(id));
                default:
                    break;
            }            
        }
    }
    
    private JSONObject createTestrailPlan(Entity<IntegTestrailPlan> integTestrailPlanEntity) throws RepositoryException, CommandException, CommandBusException, AppContextException
    {
        Entity<IntegBuildQA> qaPlanRunInfo = integQAContext.loadIntegBuildQA(integTestrailPlanEntity.getData().getIntegBuildQAInfo().getId());
        
        Long testrailProjectId = integTestrailPlanEntity.getData().getIntegBuildQAInfo().getData().getIntegProductQAInfo().getData().getIntegTestrailProjectInfo().getData().getTestrailProjectId();
        Long testrailSuiteId = integTestrailPlanEntity.getData().getIntegBuildQAInfo().getData().getIntegProductQAInfo().getData().getIntegTestrailProjectInfo().getData().getTestrailSuiteId();
        TestrailRootContext testrailInstance = new TestrailRootContext(loadTestrailPriorities());
        JSONObject testrailProject = testrailSystemCommandBus.submit(new GetProjectCommand(testrailProjectId)).getResult();
        JSONArray testrailProjectConfigurations = testrailSystemCommandBus.submit(new GetConfigurationsCommand(testrailProjectId)).getResult();
        TestrailProjectContext projectInstance = testrailInstance.newProjectInstance(testrailProject, testrailProjectConfigurations);

        TestrailTestPlanBuilder testPlanBuilder = projectInstance.newTestPlanBuilder();
        testPlanBuilder.setName(getPlanName(qaPlanRunInfo));
        testPlanBuilder.setDescription(getPlanDescription(qaPlanRunInfo));

        JSONObject testrailTestSuite = testrailSystemCommandBus.submit(new GetTestSuiteCommand(testrailSuiteId)).getResult();
        JSONArray testrailTestCases = testrailSystemCommandBus.submit(new GetTestCasesCommand(testrailProjectId, testrailSuiteId)).getResult();
        for (PlanEntry planEntry : qaPlanRunInfo.getData().getIntegProductQAInfo().getData().getProductQAInfo().getData().getPlans().get(0).getPlanEntries().values()) {
            String entryName = planEntry.getName();

            TestPlanEntryBuilder testPlanEntryBuilder = testPlanBuilder.newTestPlanEntryBuilder(entryName, testrailTestSuite, testrailTestCases);
            testPlanEntryBuilder.filterCaseIds(new TestrailCaseSelector(planEntry, testrailInstance.getTestrailPriorities()));
            testPlanEntryBuilder.filterConfigIds(new TestrailConfigurationsSelector(planEntry));
            JSONObject entry = testPlanEntryBuilder.build();
            testPlanBuilder.addEntry(entry);
        }

        JSONObject testPlanRequest = testPlanBuilder.build();
        JSONObject testPlan = testrailSystemCommandBus.submit(new AddTestPlanCommand((Long) projectInstance.getTestrailProject().get(TestrailAPI.Key.Id), testPlanRequest)).getResult();

        return testPlan;
    }

    private String getPlanName(Entity<IntegBuildQA> integBuildQAEntity)
    {
        Entity<Product> productInfo = integBuildQAEntity.getData().getBuildQAInfo().getData().getProductQAInfo().getData().getProductInfo();
        Entity<Build> buildInfo = integBuildQAEntity.getData().getBuildQAInfo().getData().getBuildInfo();
        String name = String.format("Test Plan for %s %s %s", productInfo.getData().getName(), buildInfo.getData().getBuildName(), buildInfo.getData().getPatchName());
        return name;
    }

    private String getPlanDescription(Entity<IntegBuildQA> integBuildQAEntity)
    {
        return "Created by automation";
    }

    @Override
    public Entity<IntegTestrailPlan> startIntegTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            IntegTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            IntegTestrailPlan newIntegTestrailPlan = integTestrailPlanEntity.getData().start();
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newIntegTestrailPlan);
            
            IntegTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishIntegTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public Entity<IntegTestrailPlan> loadIntegTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);

            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<IntegTestrailPlan> endIntegTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<IntegTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            IntegTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            IntegTestrailPlan newIntegTestrailPlan = integTestrailPlanEntity.getData().end();
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newIntegTestrailPlan);
            
            IntegTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishIntegTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public void handleTestrailPlanComplete(final Long testrailPlanId) throws AppContextException
    {
        try {
            Set<Entity<IntegTestrailPlan>> integTestrailPlanEntities = integTestrailPlanRepo.find(new EntitySpec<IntegTestrailPlan>(){
                @Override
                public boolean matches(Entity<IntegTestrailPlan> entity)
                {
                    if(entity.getData().getTestrailPlanId() == testrailPlanId || entity.getData().getTestrailPlanId().equals(testrailPlanId))
                    {
                        return true;
                    }
                    return false;
                }});
            
            if(integTestrailPlanEntities.size()>0)
            {
                Entity<IntegTestrailPlan> integTestrailPlanEntity = integTestrailPlanEntities.iterator().next();
                endIntegTestrailPlan(integTestrailPlanEntity.getId());
            }
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<IntegTestrailProject>> findIntegTestrailProject(EntitySpec<IntegTestrailProject> spec) throws AppContextException
    {
        try {
            Set<Entity<IntegTestrailProject>> integTestrailProjectEntities = integTestrailProjectRepo.find(spec);
            
            return integTestrailProjectEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<IntegTestrailPlan>> findIntegTestrailPlan(EntitySpec<IntegTestrailPlan> spec) throws AppContextException
    {
        try {
            Set<Entity<IntegTestrailPlan>> integTestrailPlanEntities = integTestrailPlanRepo.find(spec);
            
            return integTestrailPlanEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }
}
