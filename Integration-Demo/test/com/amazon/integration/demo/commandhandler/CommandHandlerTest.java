package com.amazon.integration.demo.commandhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
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
import com.amazon.core.qa.command.CreatePlanRunCommand;
import com.amazon.core.qa.command.CreateProjectCommand;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.core.qa.domain.vo.planrun.TestCaseResult;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.core.qa.domain.vo.project.PlanEntry;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.ReleaseSystemAssembler;
import com.amazon.core.rm.system.SimpleReleaseSystem;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.DeleteTestPlanCommand;
import com.amazon.extension.testrail.command.DeleteTestSuiteCommand;
import com.amazon.extension.testrail.command.GetTestsCommand;
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.GenericEventHandler;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.integration.demo.command.AddTestrailPlanCommand;
import com.amazon.integration.demo.command.AddTestrailResultForCaseCommand;
import com.amazon.integration.demo.command.AddTestrailSuiteCommand;
import com.amazon.integration.demo.command.GetIntegQAProjectCommand;
import com.amazon.integration.demo.command.GetTestrailPlanCommand;
import com.amazon.integration.demo.command.UpdateIntegQAProjectCommand;
import com.amazon.integration.demo.domain.entity.IntegQAProject;
import com.amazon.integration.demo.event.IntegQAPlanRunAddedEvent;
import com.amazon.integration.demo.event.IntegQAProjectCreatedEvent;
import com.amazon.integration.demo.event.IntegQAProjectPlanAddedEvent;
import com.amazon.integration.demo.system.DemoSystemAssembler;
import com.amazon.integration.demo.system.SimpleDemoSystem;

public class CommandHandlerTest
{
    AppSystem rmSystem;
    AppSystem pmSystem;
    AppSystem qaSystem;
    AppSystem testrailSystem;
    AppSystem demoSystem;
    
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
        
        pmSystem.start();
        rmSystem.start();
        qaSystem.start();
        testrailSystem.start();
        demoSystem.start();
    }
    
    @AfterTest
    public void cleanUp() throws Exception {
        demoSystem.shutdown();
        testrailSystem.shutdown();
        qaSystem.shutdown();
        rmSystem.shutdown();
        pmSystem.shutdown();
    }
    
    @Test
    public void testTriggerPreflightForCart_UseExistingProjectAndSuite() throws Exception
    {
        boolean interactive = true;
        GenericEventHandler<IntegQAProjectCreatedEvent> integQAProjectCreatedEventHandler = new GenericEventHandler<IntegQAProjectCreatedEvent>(IntegQAProjectCreatedEvent.class);
        GenericEventHandler<IntegQAProjectPlanAddedEvent> integQAProjectPlanAddedEventHandler = new GenericEventHandler<IntegQAProjectPlanAddedEvent>(IntegQAProjectPlanAddedEvent.class);
        GenericEventHandler<IntegQAPlanRunAddedEvent> integQAPlanRunAddedEventHandler = new GenericEventHandler<IntegQAPlanRunAddedEvent>(IntegQAPlanRunAddedEvent.class);
        
        demoSystem.getEventBus().registerEventHandler(IntegQAProjectCreatedEvent.class, integQAProjectCreatedEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegQAProjectPlanAddedEvent.class, integQAProjectPlanAddedEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegQAPlanRunAddedEvent.class, integQAPlanRunAddedEventHandler);
        
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
        Entity<IntegQAProject> integQAProjectEntity = demoSystem.getCommandBus().submit(new GetIntegQAProjectCommand(qaProjectEntity.getId())).getResult();
        Assert.assertEquals(integQAProjectEntity.getId(), integQAProjectCreatedEventHandler.getEvent().getIntegQAProjectId());
        
//        /**
//         * create testrail project
//         */
//        JSONObject testrailProject = demoSystem.getCommandBus().submit(new AddTestrailProjectCommand(qaProjectEntity.getId())).getResult();
        
        /**
         * add plan to project
         */      
        Plan plan = preflightPlan(null);
        PlanEntry planEntry = plan.getPlanEntries().values().iterator().next();
        AddPlanToProjectCommand addPlanToProjectCommand = new AddPlanToProjectCommand(qaProjectEntity.getId(), plan);
        qaProjectEntity = qaSystem.getCommandBus().submit(addPlanToProjectCommand).getResult();
        
        /**
         * wait the IntegQAProject is updated automatically by event listener
         */
        integQAProjectPlanAddedEventHandler.waitUntilInvokedOrTimeout(5);
        integQAProjectEntity = demoSystem.getCommandBus().submit(new GetIntegQAProjectCommand(qaProjectEntity.getId())).getResult();
        Assert.assertTrue(integQAProjectEntity.getData().getPlanSuiteIds().containsKey(integQAProjectPlanAddedEventHandler.getEvent().getPlanName()), integQAProjectEntity.getData().getPlanSuiteIds()+" "+integQAProjectPlanAddedEventHandler.getEvent().getPlanName());

        /**
         * associate the testrail project and plan id for the plan we created.
         * NOTE. In this test, we don't create new, but use existing ones
         */
        Map<String, Long> newPlanSuiteIdMaps = integQAProjectEntity.getData().getPlanSuiteIds();
        newPlanSuiteIdMaps.put(plan.getName(), 21L);
        IntegQAProject newIntegQAProject = new IntegQAProject(integQAProjectEntity.getData().getQaProjectId(), 2L, newPlanSuiteIdMaps);
        integQAProjectEntity = demoSystem.getCommandBus().submit(new UpdateIntegQAProjectCommand(qaProjectEntity.getId(), newIntegQAProject)).getResult();
        
        /**
         * create a build
         */
        Build rmBuild = new Build(pmProductEntity,"Build X", "Base");
        CreateBuildCommand createBuildCommand = new CreateBuildCommand(rmBuild.getProductInfo().getId(), rmBuild.getBuildName(), rmBuild.getPatchName());
        Entity<Build> rmBuildEntity = rmSystem.getCommandBus().submit(createBuildCommand).getResult();
        
        /**
         * create run to test the build using a test plan in the project
         */
        PlanRun qaPlanRun = new PlanRun(qaProjectEntity, plan, rmBuildEntity);
        CreatePlanRunCommand createPlanRunCommand = new CreatePlanRunCommand(qaPlanRun.getProjectInfo().getId(), plan.getName(), qaPlanRun.getBuildInfo().getId());
        Entity<PlanRun> qaPlanRunEntity = qaSystem.getCommandBus().submit(createPlanRunCommand).getResult();
        
        /**
         * wait the QAPlanRun is created automatically by event listener. Then populate testrail testplan
         */
        integQAPlanRunAddedEventHandler.waitUntilInvokedOrTimeout(5);
        JSONObject addedTestrailTestPlan = demoSystem.getCommandBus().submit(new AddTestrailPlanCommand(qaPlanRunEntity.getId())).getResult(60);
        Assert.assertNotNull(addedTestrailTestPlan);
        
        /**
         * get test plan in Testrail
         * The testrail test plan is created automatically when a qa plan run is created. you may need wait for a while to get it.
         */  
        GetTestrailPlanCommand getTestrailPlanCommand = new GetTestrailPlanCommand(qaPlanRunEntity.getId());
        JSONObject testrailTestPlan = demoSystem.getCommandBus().submit(getTestrailPlanCommand).getResult();
        JSONArray testrailTestPlanEntries = (JSONArray) testrailTestPlan.get(TestrailAPI.Key.Entries);
        JSONArray testrailTestPlanEntryRuns = (JSONArray) ((JSONObject)testrailTestPlanEntries.get(0)).get(TestrailAPI.Key.Runs);
        JSONObject testrailTestPlanEntryRun = (JSONObject) testrailTestPlanEntryRuns.get(0);
        Long testrailTestPlanEntryRunId = (Long) testrailTestPlanEntryRun.get(TestrailAPI.Key.Id);
        JSONArray testrailTestPlanEntryRunTests = testrailSystem.getCommandBus().submit(new GetTestsCommand(testrailTestPlanEntryRunId)).getResult();
        
        checkpoint(interactive, "type a character to start uploading result into testplan in testrail");
        
        /**
         * update testrail test plan with one test result
         */
        for(Object o: testrailTestPlanEntryRunTests)
        {
            JSONObject testrailTestPlanEntryRunTest =  (JSONObject) o;
            Long testrailCaseId = (Long) testrailTestPlanEntryRunTest.get(TestrailAPI.Key.Case_Id);
            Calendar cal = Calendar.getInstance();
            Date startDate = cal.getTime();
            cal.roll(Calendar.SECOND, 35);
            Date endDate = cal.getTime();
            TestCaseResult testCaseResult = new TestCaseResult(TestCaseResult.Status.Pass, "Hi, it is Navy.", startDate, endDate);
            JSONObject updatedResult = demoSystem.getCommandBus().submit(new AddTestrailResultForCaseCommand(qaPlanRunEntity.getId(), qaProjectEntity.getId(), plan.getName(), planEntry.getName(), testrailCaseId, testCaseResult)).getResult();
            Assert.assertNotNull(updatedResult);
            Assert.assertNotNull(updatedResult.get(TestrailAPI.Key.Id));
        }
        
        checkpoint(interactive, "type a character to start deleting the testplan in testrail");
        
        /**
         * delete the plan we created
         */
        testrailSystem.getCommandBus().submit(new DeleteTestPlanCommand((Long) testrailTestPlan.get(TestrailAPI.Key.Id))).getResult();
        System.out.println("finished deleting the testplan in testrail");
    }
    
    @Test
    public void testTriggerDemoPlanForCart_UseExistingCartProject() throws Exception
    {
        boolean interactive = true;
        GenericEventHandler<IntegQAProjectCreatedEvent> integQAProjectCreatedEventHandler = new GenericEventHandler<IntegQAProjectCreatedEvent>(IntegQAProjectCreatedEvent.class);
        GenericEventHandler<IntegQAProjectPlanAddedEvent> integQAProjectPlanAddedEventHandler = new GenericEventHandler<IntegQAProjectPlanAddedEvent>(IntegQAProjectPlanAddedEvent.class);
        GenericEventHandler<IntegQAPlanRunAddedEvent> integQAPlanRunAddedEventHandler = new GenericEventHandler<IntegQAPlanRunAddedEvent>(IntegQAPlanRunAddedEvent.class);
        
        demoSystem.getEventBus().registerEventHandler(IntegQAProjectCreatedEvent.class, integQAProjectCreatedEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegQAProjectPlanAddedEvent.class, integQAProjectPlanAddedEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegQAPlanRunAddedEvent.class, integQAPlanRunAddedEventHandler);
        
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
        Entity<IntegQAProject> integQAProjectEntity = demoSystem.getCommandBus().submit(new GetIntegQAProjectCommand(qaProjectEntity.getId())).getResult();
        Assert.assertEquals(integQAProjectEntity.getId(), integQAProjectCreatedEventHandler.getEvent().getIntegQAProjectId());
        
        /**
         * associate the testrail project and plan id for the plan we created.
         * NOTE. In this test, we don't create new, but use existing ones
         */
        Map<String, Long> newPlanSuiteIdMaps = integQAProjectEntity.getData().getPlanSuiteIds();
        IntegQAProject newIntegQAProject = new IntegQAProject(integQAProjectEntity.getData().getQaProjectId(), 2L, newPlanSuiteIdMaps);
        integQAProjectEntity = demoSystem.getCommandBus().submit(new UpdateIntegQAProjectCommand(qaProjectEntity.getId(), newIntegQAProject)).getResult();
        
        checkpoint(interactive, "type a character to start adding plan. Note this will cause a testsuite in testrail.");
        
        /**
         * add plan to project
         */
        Plan plan = new Plan("demo plan", "demo run for ");
        plan = plan.addEntry(makePlanEntry(plan.getEntryBaseName(),null, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        PlanEntry planEntry = plan.getPlanEntries().values().iterator().next();
        AddPlanToProjectCommand addPlanToProjectCommand = new AddPlanToProjectCommand(qaProjectEntity.getId(), plan);
        qaProjectEntity = qaSystem.getCommandBus().submit(addPlanToProjectCommand).getResult();
        
        /**
         * wait the IntegQAProject is updated automatically by event listener
         */
        integQAProjectPlanAddedEventHandler.waitUntilInvokedOrTimeout(5);
        integQAProjectEntity = demoSystem.getCommandBus().submit(new GetIntegQAProjectCommand(qaProjectEntity.getId())).getResult();
        Assert.assertTrue(integQAProjectEntity.getData().getPlanSuiteIds().containsKey(integQAProjectPlanAddedEventHandler.getEvent().getPlanName()), integQAProjectEntity.getData().getPlanSuiteIds()+" "+integQAProjectPlanAddedEventHandler.getEvent().getPlanName());
        
        /**
         * create testsuite in testrail 
         */
        JSONObject testrailTestSuite = demoSystem.getCommandBus().submit(new AddTestrailSuiteCommand(qaProjectEntity.getId(), plan.getName())).getResult();
        System.out.println("testrail testsuite created. "+testrailTestSuite.toJSONString());
        
        checkpoint(interactive, "type a character to confirm that you have added some tests that satisfy (Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1) in the newly created testsuite in testrail");
        
        checkpoint(interactive, "type a character to start adding a build.");
        
        /**
         * create a build
         */
        Build rmBuild = new Build(pmProductEntity,"Build X", "Base");
        CreateBuildCommand createBuildCommand = new CreateBuildCommand(rmBuild.getProductInfo().getId(), rmBuild.getBuildName(), rmBuild.getPatchName());
        Entity<Build> rmBuildEntity = rmSystem.getCommandBus().submit(createBuildCommand).getResult();
        
        checkpoint(interactive, "type a character to start adding a planrun. this will cause a new testplan in testrail");
        
        /**
         * create run to test the build using a test plan in the project
         */
        PlanRun qaPlanRun = new PlanRun(qaProjectEntity, plan, rmBuildEntity);
        CreatePlanRunCommand createPlanRunCommand = new CreatePlanRunCommand(qaPlanRun.getProjectInfo().getId(), plan.getName(), qaPlanRun.getBuildInfo().getId());
        Entity<PlanRun> qaPlanRunEntity = qaSystem.getCommandBus().submit(createPlanRunCommand).getResult();
        
        /**
         * wait the QAPlanRun is created automatically by event listener. Then populate testrail testplan
         */
        integQAPlanRunAddedEventHandler.waitUntilInvokedOrTimeout(5);
        JSONObject addedTestrailTestPlan = demoSystem.getCommandBus().submit(new AddTestrailPlanCommand(qaPlanRunEntity.getId())).getResult(60);
        Assert.assertNotNull(addedTestrailTestPlan);
        
        /**
         * get test plan in Testrail
         * The testrail test plan is created automatically when a qa plan run is created. you may need wait for a while to get it.
         */  
        GetTestrailPlanCommand getTestrailPlanCommand = new GetTestrailPlanCommand(qaPlanRunEntity.getId());
        JSONObject testrailTestPlan = demoSystem.getCommandBus().submit(getTestrailPlanCommand).getResult();
        JSONArray testrailTestPlanEntries = (JSONArray) testrailTestPlan.get(TestrailAPI.Key.Entries);
        JSONArray testrailTestPlanEntryRuns = (JSONArray) ((JSONObject)testrailTestPlanEntries.get(0)).get(TestrailAPI.Key.Runs);
        JSONObject testrailTestPlanEntryRun = (JSONObject) testrailTestPlanEntryRuns.get(0);
        Long testrailTestPlanEntryRunId = (Long) testrailTestPlanEntryRun.get(TestrailAPI.Key.Id);
        JSONArray testrailTestPlanEntryRunTests = testrailSystem.getCommandBus().submit(new GetTestsCommand(testrailTestPlanEntryRunId)).getResult();
        
        checkpoint(interactive, "type a character to start populating result into testplan in testrail");
        
        /**
         * update testrail test plan with one test result
         */
        for(Object o: testrailTestPlanEntryRunTests)
        {
            JSONObject testrailTestPlanEntryRunTest =  (JSONObject) o;
            Long testrailCaseId = (Long) testrailTestPlanEntryRunTest.get(TestrailAPI.Key.Case_Id);
            Calendar cal = Calendar.getInstance();
            Date startDate = cal.getTime();
            cal.roll(Calendar.SECOND, 35);
            Date endDate = cal.getTime();
            TestCaseResult testCaseResult = new TestCaseResult(TestCaseResult.Status.Pass, "Hi, it is Navy.", startDate, endDate);
            JSONObject updatedResult = demoSystem.getCommandBus().submit(new AddTestrailResultForCaseCommand(qaPlanRunEntity.getId(), qaProjectEntity.getId(), plan.getName(), planEntry.getName(), testrailCaseId, testCaseResult)).getResult();
            Assert.assertNotNull(updatedResult);
            Assert.assertNotNull(updatedResult.get(TestrailAPI.Key.Id));
        }
        
        checkpoint(interactive, "type a character to start deleting the testplan in testrail");
        
        /**
         * delete the suite and plan we created
         */
        testrailSystem.getCommandBus().submit(new DeleteTestPlanCommand((Long) testrailTestPlan.get(TestrailAPI.Key.Id))).getResult();
        testrailSystem.getCommandBus().submit(new DeleteTestSuiteCommand((Long) testrailTestSuite.get(TestrailAPI.Key.Id))).getResult();
        System.out.println("finished deleting the testplan and testsuite in testrail");
    }
    
    private void checkpoint(boolean interactive, String message) throws IOException
    {       
        System.out.println(String.format("interactive[%s]", interactive));
        if(interactive)
        {
            System.out.println(message);
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } else {
            System.out.println(message);
        }
    }
    
//    @Test
//    public void test3() throws IOException {
//        System.out.println("begin");
//        System.in.read();
//        System.out.println("end");
//    }
    
    private List<PlanEntry> makePlanEntries(Plan p, Entity<TestSuite> testSuiteInfo) {
        List<PlanEntry> planEntries = new ArrayList<PlanEntry>();
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.UK, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.CN, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.JP, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.DE, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.FR, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.IT, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.CA, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.ES, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.IN, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.BR, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"US Desktop JS Error",testSuiteInfo, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P0));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"US under Chrome & IE11",testSuiteInfo, Locale.US, Platform.Desktop, Browser.Any, View.Desktop, Priority.P0));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"DE under FireFox",testSuiteInfo, Locale.DE, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"JP under IE10",testSuiteInfo, Locale.JP, Platform.Desktop, Browser.IE10, View.Desktop, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"CN under IE6 & IE8",testSuiteInfo, Locale.CN, Platform.Desktop, Browser.Any, View.Desktop, Priority.P1));
        
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"US Phone Web JS Error",testSuiteInfo, Locale.US, Platform.Any, Browser.Any, View.MobileWeb, Priority.P0));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"US Phone Web",testSuiteInfo, Locale.US, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"UK Phone Web",testSuiteInfo, Locale.UK, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"CN Phone Web",testSuiteInfo, Locale.CN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"JP Phone Web",testSuiteInfo, Locale.JP, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"DE Phone Web",testSuiteInfo, Locale.DE, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"FR Phone Web",testSuiteInfo, Locale.FR, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"CN Phone Web",testSuiteInfo, Locale.CN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"CA Phone Web",testSuiteInfo, Locale.CA, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"ES Phone Web",testSuiteInfo, Locale.ES, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"IT Phone Web",testSuiteInfo, Locale.IT, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"IN Phone Web",testSuiteInfo, Locale.IN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"BR Phone Web",testSuiteInfo, Locale.BR, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        
        
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"US Android Phone App",testSuiteInfo, Locale.US, Platform.Any, Browser.App, View.MobileApp, Priority.P1));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"All locales on tablet apps(Shared among locales, part on Android tablet app, part on IOS tablet app and the others on Kindle G6 and G5)",testSuiteInfo, Locale.Any, Platform.Any, Browser.Any, View.TabletApp, Priority.P0));
        planEntries.add(makePlanEntry(p.getEntryBaseName(),"Win 8 App - US",testSuiteInfo, Locale.US, Platform.Any, Browser.Any, View.MobileApp, Priority.P0));  
        return planEntries;
    }
    
    private Plan preflightPlan(Entity<TestSuite> testSuiteInfo)
    {
        Plan p = new Plan("Preflight", "Test Run for ");
        for(PlanEntry planEntry: makePlanEntries(p, testSuiteInfo))
        {
            p = p.addEntry(planEntry);
        }
        return p;
    }
    
    private PlanEntry makePlanEntry(String entryBaseName, String entryName, Entity<TestSuite> testSuiteInfo, Locale locale, Platform platform, Browser browser, View view, Priority priority)
    {
        if(entryName == null)
        {
            entryName = String.format("%s %s %s %s %s", entryBaseName, view, platform, browser, locale);
        }
        return new PlanEntry(entryName, testSuiteInfo, locale, platform, browser, view, priority);
    }
    
    private PlanEntry makePlanEntry(String entryBaseName, Entity<TestSuite> testSuiteInfo, Locale locale, Platform platform, Browser browser, View view, Priority priority)
    {
        return makePlanEntry(entryBaseName, null, testSuiteInfo, locale, platform, browser, view, priority);
    }
}

