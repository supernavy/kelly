package com.amazon.integration.demo.context;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.ProductSystemAssembler;
import com.amazon.core.pm.system.SimpleProductSystem;
import com.amazon.core.qa.command.AddPlanToProjectCommand;
import com.amazon.core.qa.command.CreateProjectCommand;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.core.qa.domain.vo.project.PlanEntry;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.system.ReleaseSystemAssembler;
import com.amazon.core.rm.system.SimpleReleaseSystem;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.command.GetProjectCommand;
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.GenericEventHandler;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.integration.demo.command.AddTestrailProjectCommand;
import com.amazon.integration.demo.command.AddTestrailSuiteCommand;
import com.amazon.integration.demo.command.GetIntegQAProjectCommand;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.context.impl.IntegContextImpl;
import com.amazon.integration.demo.context.impl.IntegTestrailContextImpl;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;
import com.amazon.integration.demo.domain.entity.IntegQAProject;
import com.amazon.integration.demo.domain.entity.IntegQATestCase;
import com.amazon.integration.demo.domain.entity.IntegQATestSuite;
import com.amazon.integration.demo.event.IntegQAProjectCreatedEvent;
import com.amazon.integration.demo.event.IntegQAProjectPlanAddedEvent;
import com.amazon.integration.demo.system.DemoSystem;
import com.amazon.integration.demo.system.DemoSystemAssembler;
import com.amazon.integration.demo.system.SimpleDemoSystem;

public class ContextTest
{
    AppSystem rmSystem;
    AppSystem pmSystem;
    AppSystem qaSystem;
    AppSystem testrailSystem;
    AppSystem demoSystem;
    IntegTestrailContext integTestrailContext;

    String url = "https://rcx-testrail.amazon.com/api.php?/api/v2";
    String username = "supernavy_trash@sina.com";
    String password = "Test123";
    
    @BeforeTest
    public void init() throws Exception
    {
        pmSystem = new SimpleProductSystem("demo product", Layer.Core);
        new ProductSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleReleaseSystem("demo build", Layer.Core, pmSystem);
        new ReleaseSystemAssembler().assemble(rmSystem);
        qaSystem = new SimpleQASystem("demo qa", Layer.Core, pmSystem, rmSystem);
        new QASystemAssembler().assemble(qaSystem);
        testrailSystem = new SimpleTestrailSystem("demo testrail system", Layer.Extension);
        new TestrailSystemAssembler(url, username, password).assemble(testrailSystem);
        demoSystem = new SimpleDemoSystem("demo integration", Layer.Extension, qaSystem, testrailSystem, rmSystem, pmSystem);
        new DemoSystemAssembler().assemble(demoSystem);

        Repository<IntegQAProject> integQAProjectRepo = demoSystem.getRepository(DemoSystem.Repository_IntegQAProject);
        Repository<IntegQAPlanRun> integQAPlanRunRepo = demoSystem.getRepository(DemoSystem.Repository_IntegQAPlanRun);
        Repository<IntegQATestSuite> integQATestSuiteRepo = demoSystem.getRepository(DemoSystem.Repository_IntegQATestSuite);
        Repository<IntegQATestCase> integQATestCaseRepo = demoSystem.getRepository(DemoSystem.Repository_IntegQATestCase);

        IntegContext integContext = new IntegContextImpl(demoSystem.getEventBus(), integQAProjectRepo, integQAPlanRunRepo, integQATestSuiteRepo, integQATestCaseRepo);
        integTestrailContext = new IntegTestrailContextImpl(demoSystem.getEventBus(), integContext, demoSystem.getDependency(DemoSystem.System_QA).getCommandBus(), demoSystem.getDependency(DemoSystem.System_Testrail).getCommandBus());

        pmSystem.start();
        rmSystem.start();
        qaSystem.start();
        testrailSystem.start();
        demoSystem.start();
    }

    @AfterTest
    public void cleanUp() throws Exception
    {
        demoSystem.shutdown();
        testrailSystem.shutdown();
        qaSystem.shutdown();
        rmSystem.shutdown();
        pmSystem.shutdown();
    }

    @Test
    public void testIntegContext() throws Exception
    {
        JSONObject testrailTestPlan = integTestrailContext.createTestrailPlan("111111");
        Assert.assertNotNull(testrailTestPlan);
        Assert.assertNotNull(testrailTestPlan.get(TestrailAPI.Key.Id));
    }
    
    @Test
    public void testIntegTestrailContext_Create() throws Exception
    {
        GenericEventHandler<IntegQAProjectCreatedEvent> integQAProjectCreatedEventHandler = new GenericEventHandler<IntegQAProjectCreatedEvent>(IntegQAProjectCreatedEvent.class);
        GenericEventHandler<IntegQAProjectPlanAddedEvent> integQAProjectPlanAddedEventHandler = new GenericEventHandler<IntegQAProjectPlanAddedEvent>(IntegQAProjectPlanAddedEvent.class);
        
        demoSystem.getEventBus().registerEventHandler(IntegQAProjectCreatedEvent.class, integQAProjectCreatedEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegQAProjectPlanAddedEvent.class, integQAProjectPlanAddedEventHandler);
        
        /**
         * create product
         */
        Product pmProduct = new Product("product for demo purpose", "Amazon Shopping Cart");
        CreateProductCommand createProductCommand = new CreateProductCommand(pmProduct.getName(), pmProduct.getDesc());
        Entity<Product> pmProductEntity = pmSystem.getCommandBus().submit(createProductCommand).getResult();
        
        /**
         * create project
         */
        Project qaProject = new Project(pmProductEntity, "Project for demo purpose");
        CreateProjectCommand createProjectCommand = new CreateProjectCommand(qaProject.getName(), qaProject.getProductInfo().getId());
        Entity<Project> qaProjectEntity = qaSystem.getCommandBus().submit(createProjectCommand).getResult();
        
        /**
         * wait for the integQAProject is created by the demo system automatically by event listener
         */
        integQAProjectCreatedEventHandler.waitUntilInvokedOrTimeout(5);
        Entity<IntegQAProject> integQAProject = demoSystem.getCommandBus().submit(new GetIntegQAProjectCommand(qaProjectEntity.getId())).getResult();
        Assert.assertEquals(integQAProject.getId(), integQAProjectCreatedEventHandler.getEvent().getIntegQAProjectId());
        
        /**
         * create testrail project
         */
        JSONObject testrailProject = demoSystem.getCommandBus().submit(new AddTestrailProjectCommand(qaProjectEntity.getId())).getResult();
        
        /**
         * add plan to project
         */      
        Plan plan = new Plan("Test Plan for demo purpuse","Run for ");
        PlanEntry planEntry = new PlanEntry("entry for demo", null, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1);
        plan = plan.addEntry(planEntry);
        AddPlanToProjectCommand addPlanToProjectCommand = new AddPlanToProjectCommand(qaProjectEntity.getId(), plan);
        qaProjectEntity = qaSystem.getCommandBus().submit(addPlanToProjectCommand).getResult();
        
        /**
         * wait the IntegQAProject is updated automatically by event listener
         */
        integQAProjectPlanAddedEventHandler.waitUntilInvokedOrTimeout(5);
        integQAProject = demoSystem.getCommandBus().submit(new GetIntegQAProjectCommand(qaProjectEntity.getId())).getResult();
        Assert.assertTrue(integQAProject.getData().getPlanSuiteIds().containsKey(integQAProjectPlanAddedEventHandler.getEvent().getPlanName()), integQAProject.getData().getPlanSuiteIds()+" "+integQAProjectPlanAddedEventHandler.getEvent().getPlanName());
        
        /**
         * create testrail testsuite
         */
        JSONObject testrailTestSuite = demoSystem.getCommandBus().submit(new AddTestrailSuiteCommand(qaProjectEntity.getId(), plan.getName())).getResult();
        System.out.println(testrailTestSuite.toJSONString());
        
        // verify testrail
        testrailProject = integTestrailContext.loadTestrailProject(qaProjectEntity.getId());
        Assert.assertNotNull(testrailProject);
        Assert.assertNotNull(testrailProject.get(TestrailAPI.Key.Id));

        JSONObject testrailSuite = integTestrailContext.loadTestrailTestSuite(qaProjectEntity.getId(), plan.getName());
        Assert.assertNotNull(testrailSuite);
        Assert.assertNotNull(testrailSuite.get(TestrailAPI.Key.Id));
        
        /**
         * delete the testrail project
         */
        testrailSystem.getCommandBus().submit(new DeleteProjectCommand((Long) testrailProject.get(TestrailAPI.Key.Id))).getResult();
        
        try {
            testrailProject = testrailSystem.getCommandBus().submit(new GetProjectCommand((Long) testrailProject.get(TestrailAPI.Key.Id))).getResult();
            Assert.fail("should not be here");
        } catch (CommandException ex)
        {
            System.out.println("get expected exception");
        }
    }
}
