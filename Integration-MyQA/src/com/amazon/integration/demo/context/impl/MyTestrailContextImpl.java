package com.amazon.integration.demo.context.impl;

import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.command.ProductQAGetCommand;
import com.amazon.core.qa.domain.entity.ProductQA;
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
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.entity.MyProductQA;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;
import com.amazon.integration.demo.domain.event.MyTestrailPlanEndEvent;
import com.amazon.integration.demo.domain.event.MyTestrailPlanNewEvent;
import com.amazon.integration.demo.domain.event.MyTestrailPlanReadyEvent;
import com.amazon.integration.demo.domain.event.MyTestrailPlanTestInProgressEvent;
import com.amazon.integration.demo.domain.event.MyTestrailProjectEndEvent;
import com.amazon.integration.demo.domain.event.MyTestrailProjectNewEvent;
import com.amazon.integration.demo.domain.event.MyTestrailProjectReadyEvent;
import com.amazon.integration.demo.system.DemoSystem;

public class MyTestrailContextImpl extends AbsAppContextImpl implements MyTestrailContext
{
    Repository<MyTestrailProject> integTestrailProjectRepo;
    Repository<MyTestrailPlan> integTestrailPlanRepo;

    CommandBus testrailSystemCommandBus;
    CommandBus qaSystemCommandBus;
    
    MyQAContext integQAContext;
    
    public MyTestrailContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.integTestrailProjectRepo = appSystem.getRepository(DemoSystem.Repository_MyTestrailProject);
        this.integTestrailPlanRepo = appSystem.getRepository(DemoSystem.Repository_MyTestrailPlan);
        
        this.qaSystemCommandBus = appSystem.getDependency(DemoSystem.System_QA).getCommandBus();
        this.testrailSystemCommandBus = appSystem.getDependency(DemoSystem.System_Testrail).getCommandBus();
    }
    
    public void setMyQAContext(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }
    
    @Override
    public Entity<MyTestrailProject> newMyTestrailProject(String integProductQAId) throws AppContextException
    {   
        try {
            Entity<MyProductQA> integProductQAEntity = integQAContext.loadMyProductQA(integProductQAId);
            MyTestrailProject integTestrailProject = new MyTestrailProject(integProductQAEntity);
            Entity<MyTestrailProject> integTestrailProjectEntity = integTestrailProjectRepo.createEntity(integTestrailProject);
            publishEvent(new MyTestrailProjectNewEvent(integTestrailProjectEntity.getId()));
            return integTestrailProjectEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
    
    private void publishMyTestrailProjectEvent(MyTestrailProject.Status oldStatus, MyTestrailProject.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Ready: publishEvent(new MyTestrailProjectReadyEvent(id)); break;
                case End: publishEvent(new MyTestrailProjectEndEvent(id)); break;
                default:
                    break;
            }            
        }
    }

    @Override
    public Entity<MyTestrailProject> loadMyTestrailProject(String integTestrailProjectId) throws AppContextException
    {
        Entity<MyTestrailProject> integTestrailProjectEntity;
        try {
            integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            return integTestrailProjectEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public Entity<MyTestrailProject> initMyTestrailProject(String integTestrailProjectId) throws AppContextException
    {        
        try {
            Entity<MyTestrailProject> integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            MyTestrailProject.Status oldStatus = integTestrailProjectEntity.getData().getStatus();
            
            JSONObject testrailProject = createTestrailProject(integTestrailProjectEntity);
            Long testrailProjectId = (Long) testrailProject.get(TestrailAPI.Key.Id);
            JSONObject testrailSuite = createTestrailSuite(integTestrailProjectEntity, testrailProjectId);
            Long testrailSuiteId = (Long) testrailSuite.get(TestrailAPI.Key.Id);
            MyTestrailProject newMyTestrailProject = integTestrailProjectEntity.getData().updateTestrailInfo(testrailProjectId, testrailSuiteId);
            integTestrailProjectEntity = integTestrailProjectRepo.updateEntity(integTestrailProjectEntity.getId(), newMyTestrailProject);
            MyTestrailProject.Status newStatus = integTestrailProjectEntity.getData().getStatus();
            
            publishMyTestrailProjectEvent(oldStatus, newStatus, integTestrailProjectEntity.getId());
            
            return integTestrailProjectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private JSONObject createTestrailProject(Entity<MyTestrailProject> integTestrailProjectEntity) throws CommandException, CommandBusException
    {       
        TestrailRootContext testrailInstance = new TestrailRootContext(loadTestrailPriorities());
        TestrailProjectBuilder testrailProjectBuilder = testrailInstance.newProjectBuilder();
        JSONObject data = testrailProjectBuilder.setName(mapTestrailProjectName(integTestrailProjectEntity)).setDescription(mapTestrailProjectDescription(integTestrailProjectEntity)).build();

        JSONObject testrailProject = testrailSystemCommandBus.submit(new AddProjectCommand(data)).getResult();
        return testrailProject;
    }
    
    @SuppressWarnings("unchecked")
    private JSONObject createTestrailSuite(Entity<MyTestrailProject> integTestrailProjectEntity, Long testrailProjectId) throws CommandException, CommandBusException
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
    
    private String mapTestrailProjectName(Entity<MyTestrailProject> integTestrailProjectEntity)
    {
        return "QA for "+integTestrailProjectEntity.getData().getMyProductQAInfo().getData().getProductQAInfo().getData().getProductInfo().getData().getName();
    }
    
    private String mapTestrailProjectDescription(Entity<MyTestrailProject> integTestrailProjectEntity)
    {
        return "no description";
    }

    @Override
    public Entity<MyTestrailProject> endMyTestrailProject(String integTestrailProjectId) throws AppContextException
    {
        Entity<MyTestrailProject> integTestrailProjectEntity;
        try {
            integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            MyTestrailProject.Status oldStatus = integTestrailProjectEntity.getData().getStatus();
            testrailSystemCommandBus.submit(new DeleteProjectCommand(integTestrailProjectEntity.getData().getTestrailProjectId())).getResult();
            MyTestrailProject newMyTestrailProject = integTestrailProjectEntity.getData().end();
            integTestrailProjectEntity = integTestrailProjectRepo.updateEntity(integTestrailProjectId, newMyTestrailProject);
            MyTestrailProject.Status newStatus = integTestrailProjectEntity.getData().getStatus();
            publishMyTestrailProjectEvent(oldStatus, newStatus, integTestrailProjectId);
            return integTestrailProjectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
        
    }

    @Override
    public Entity<MyTestrailPlan> newMyTestrailPlan(String integBuildQAId) throws AppContextException
    {
        try {
            Entity<MyBuildQA> integBuildQAEntity = integQAContext.loadMyBuildQA(integBuildQAId);
            MyTestrailPlan integTestrailPlan = new MyTestrailPlan(integBuildQAEntity);
            Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.createEntity(integTestrailPlan);
            publishEvent(new MyTestrailPlanNewEvent(integTestrailPlanEntity.getId()));
            return integTestrailPlanEntity;
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyTestrailPlan> initMyTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            MyTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            JSONObject testrailPlan = createTestrailPlan(integTestrailPlanEntity);
            Long testrailPlanId = (Long) testrailPlan.get(TestrailAPI.Key.Id);
            MyTestrailPlan newMyTestrailPlan = integTestrailPlanEntity.getData().updateTestrailPlanId(testrailPlanId);
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newMyTestrailPlan);
            
            MyTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishMyTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
        
    }
    
    private void publishMyTestrailPlanEvent(MyTestrailPlan.Status oldStatus, MyTestrailPlan.Status newStatus, String id) throws AppContextException {
        if(!oldStatus.equals(newStatus))
        {
            switch(newStatus)
            {
                case Ready: publishEvent(new MyTestrailPlanReadyEvent(id)); break;
                case TestInProgress: publishEvent(new MyTestrailPlanTestInProgressEvent(id)); break;
                case End: publishEvent(new MyTestrailPlanEndEvent(id)); break;
                default:
                    break;
            }            
        }
    }
    
    private JSONObject createTestrailPlan(Entity<MyTestrailPlan> integTestrailPlanEntity) throws RepositoryException, CommandException, CommandBusException, AppContextException
    {
        Entity<MyBuildQA> qaPlanRunInfo = integQAContext.loadMyBuildQA(integTestrailPlanEntity.getData().getMyBuildQAInfo().getId());
        
        Long testrailProjectId = integTestrailPlanEntity.getData().getMyBuildQAInfo().getData().getMyProductQAInfo().getData().getMyTestrailProjectInfo().getData().getTestrailProjectId();
        Long testrailSuiteId = integTestrailPlanEntity.getData().getMyBuildQAInfo().getData().getMyProductQAInfo().getData().getMyTestrailProjectInfo().getData().getTestrailSuiteId();
        TestrailRootContext testrailInstance = new TestrailRootContext(loadTestrailPriorities());
        JSONObject testrailProject = testrailSystemCommandBus.submit(new GetProjectCommand(testrailProjectId)).getResult();
        JSONArray testrailProjectConfigurations = testrailSystemCommandBus.submit(new GetConfigurationsCommand(testrailProjectId)).getResult();
        TestrailProjectContext projectInstance = testrailInstance.newProjectInstance(testrailProject, testrailProjectConfigurations);

        TestrailTestPlanBuilder testPlanBuilder = projectInstance.newTestPlanBuilder();
        testPlanBuilder.setName(getPlanName(qaPlanRunInfo));
        testPlanBuilder.setDescription(getPlanDescription(qaPlanRunInfo));

        JSONObject testrailTestSuite = testrailSystemCommandBus.submit(new GetTestSuiteCommand(testrailSuiteId)).getResult();
        JSONArray testrailTestCases = testrailSystemCommandBus.submit(new GetTestCasesCommand(testrailProjectId, testrailSuiteId)).getResult();
        Entity<ProductQA> productQAEntity = qaSystemCommandBus.submit(new ProductQAGetCommand(qaPlanRunInfo.getData().getMyProductQAInfo().getData().getProductQAInfo().getId())).getResult();
        System.out.println("===="+productQAEntity.getData().getPlans().values().iterator().next());
        for (PlanEntry planEntry : productQAEntity.getData().getPlans().values().iterator().next().getPlanEntries().values()) {
            String entryName = planEntry.getName();

            TestPlanEntryBuilder testPlanEntryBuilder = testPlanBuilder.newTestPlanEntryBuilder(entryName, testrailTestSuite, testrailTestCases);
            testPlanEntryBuilder.filterCaseIds(new TestrailCaseSelector(planEntry, testrailInstance.getTestrailPriorities()));
            //don't use configuration
            //            testPlanEntryBuilder.filterConfigIds(new TestrailConfigurationsSelector(planEntry));
            JSONObject entry = testPlanEntryBuilder.build();
            testPlanBuilder.addEntry(entry);
        }

        JSONObject testPlanRequest = testPlanBuilder.build();
        JSONObject testPlan = testrailSystemCommandBus.submit(new AddTestPlanCommand((Long) projectInstance.getTestrailProject().get(TestrailAPI.Key.Id), testPlanRequest)).getResult();

        return testPlan;
    }

    private String getPlanName(Entity<MyBuildQA> integBuildQAEntity)
    {
        Entity<Product> productInfo = integBuildQAEntity.getData().getBuildQAInfo().getData().getProductQAInfo().getData().getProductInfo();
        Entity<Build> buildInfo = integBuildQAEntity.getData().getBuildQAInfo().getData().getBuildInfo();
        String name = String.format("Test Plan for %s %s %s", productInfo.getData().getName(), buildInfo.getData().getBuildName(), buildInfo.getData().getPatchName());
        return name;
    }

    private String getPlanDescription(Entity<MyBuildQA> integBuildQAEntity)
    {
        return "Created by automation";
    }

    @Override
    public Entity<MyTestrailPlan> startMyTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            MyTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            MyTestrailPlan newMyTestrailPlan = integTestrailPlanEntity.getData().start();
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newMyTestrailPlan);
            
            MyTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishMyTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public Entity<MyTestrailPlan> loadMyTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);

            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyTestrailPlan> endMyTestrailPlan(String integTestrailPlanId) throws AppContextException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            MyTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            MyTestrailPlan newMyTestrailPlan = integTestrailPlanEntity.getData().end();
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newMyTestrailPlan);
            
            MyTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishMyTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public void handleTestrailPlanComplete(final Long testrailPlanId) throws AppContextException
    {
        try {
            Set<Entity<MyTestrailPlan>> integTestrailPlanEntities = integTestrailPlanRepo.find(new EntitySpec<MyTestrailPlan>(){
                @Override
                public boolean matches(Entity<MyTestrailPlan> entity)
                {
                    if(entity.getData().getTestrailPlanId() == testrailPlanId || entity.getData().getTestrailPlanId().equals(testrailPlanId))
                    {
                        return true;
                    }
                    return false;
                }});
            
            if(integTestrailPlanEntities.size()>0)
            {
                Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanEntities.iterator().next();
                endMyTestrailPlan(integTestrailPlanEntity.getId());
            }
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<MyTestrailProject>> findMyTestrailProject(EntitySpec<MyTestrailProject> spec) throws AppContextException
    {
        try {
            Set<Entity<MyTestrailProject>> integTestrailProjectEntities = integTestrailProjectRepo.find(spec);
            
            return integTestrailProjectEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Set<Entity<MyTestrailPlan>> findMyTestrailPlan(EntitySpec<MyTestrailPlan> spec) throws AppContextException
    {
        try {
            Set<Entity<MyTestrailPlan>> integTestrailPlanEntities = integTestrailPlanRepo.find(spec);
            
            return integTestrailPlanEntities;
        } catch (Exception e)
        {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyTestrailProject> updateMyTestrailProject(String integTestrailProjectId, MyTestrailProject myTestrailProject) throws AppContextException
    {
        Entity<MyTestrailProject> integTestrailProjectEntity;
        try {
            integTestrailProjectEntity = integTestrailProjectRepo.load(integTestrailProjectId);
            MyTestrailProject.Status oldStatus = integTestrailProjectEntity.getData().getStatus();
            
            /*
             * It is not safe to delete old projects
             */
            //testrailSystemCommandBus.submit(new DeleteProjectCommand(integTestrailProjectEntity.getData().getTestrailProjectId())).getResult();
            MyTestrailProject newMyTestrailProject = integTestrailProjectEntity.getData().updateTestrailInfo(myTestrailProject.getTestrailProjectId(), myTestrailProject.getTestrailSuiteId());
            integTestrailProjectEntity = integTestrailProjectRepo.updateEntity(integTestrailProjectId, newMyTestrailProject);
            MyTestrailProject.Status newStatus = integTestrailProjectEntity.getData().getStatus();
            publishMyTestrailProjectEvent(oldStatus, newStatus, integTestrailProjectId);
            return integTestrailProjectEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<MyTestrailPlan> updateMyTestrailPlan(String integTestrailPlanId, MyTestrailPlan myTestrailPlan) throws AppContextException
    {
        try {
            Entity<MyTestrailPlan> integTestrailPlanEntity = integTestrailPlanRepo.load(integTestrailPlanId);
            MyTestrailPlan.Status oldStatus = integTestrailPlanEntity.getData().getStatus();
            
            MyTestrailPlan newMyTestrailPlan = integTestrailPlanEntity.getData().updateTestrailPlanId(myTestrailPlan.getTestrailPlanId());
            integTestrailPlanEntity = integTestrailPlanRepo.updateEntity(integTestrailPlanId, newMyTestrailPlan);
            
            MyTestrailPlan.Status newStatus = integTestrailPlanEntity.getData().getStatus();
            publishMyTestrailPlanEvent(oldStatus, newStatus, integTestrailPlanId);
            
            return integTestrailPlanEntity;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
}
