package com.amazon.integration.demo.commandhandler;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.PMSystemAssembler;
import com.amazon.core.pm.system.SimplePMSystem;
import com.amazon.core.qa.command.ProductQAAddPlanCommand;
import com.amazon.core.qa.command.ProductQANewCommand;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.core.qa.domain.vo.productqa.PlanEntry;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.system.RMSystemAssembler;
import com.amazon.core.rm.system.SimpleRMSystem;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.GenericEventHandler;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.integration.demo.command.IntegProductQAGetCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectGetCommand;
import com.amazon.integration.demo.context.ExternalSignoffContext;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.context.impl.ExternalSignoffContextImpl;
import com.amazon.integration.demo.context.impl.IntegQAContextImpl;
import com.amazon.integration.demo.context.impl.IntegTestrailContextImpl;
import com.amazon.integration.demo.domain.entity.IntegProductQA;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;
import com.amazon.integration.demo.domain.event.IntegBuildQANewEvent;
import com.amazon.integration.demo.domain.event.IntegProductQANewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanNewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectNewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectReadyEvent;
import com.amazon.integration.demo.system.DemoSystemAssembler;
import com.amazon.integration.demo.system.SimpleDemoSystem;

public class CommandHandlerTest
{
    AppSystem rmSystem;
    AppSystem pmSystem;
    AppSystem qaSystem;
    AppSystem testrailSystem;
    AppSystem demoSystem;
    
    IntegQAContext integQAContext;
    IntegTestrailContext integTestrailContext;
    ExternalSignoffContext externalSignoffContext;

    String url = "https://rcx-testrail.amazon.com/api.php?/api/v2";
    String username = "supernavy_trash@sina.com";
    String password = "Test123";
    
    @BeforeTest
    public void init() throws Exception
    {
        pmSystem = new SimplePMSystem("demo product", Layer.Core);
        new PMSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleRMSystem("demo build", Layer.Core, pmSystem);
        new RMSystemAssembler().assemble(rmSystem);
        qaSystem = new SimpleQASystem("demo qa", Layer.Core, pmSystem, rmSystem);
        new QASystemAssembler().assemble(qaSystem);
        testrailSystem = new SimpleTestrailSystem("demo testrail system", Layer.Extension);
        new TestrailSystemAssembler(url, username, password).assemble(testrailSystem);
        demoSystem = new SimpleDemoSystem("demo integration", Layer.Extension, qaSystem, testrailSystem, rmSystem, pmSystem);
        new DemoSystemAssembler().assemble(demoSystem);

        IntegQAContextImpl integQAContext = new IntegQAContextImpl(demoSystem);
        IntegTestrailContextImpl integTestrailContext = new IntegTestrailContextImpl(demoSystem);
        ExternalSignoffContextImpl externalSignoffContext = new ExternalSignoffContextImpl(demoSystem);
        
        integQAContext.setIntegTestrailContext(integTestrailContext);
        integTestrailContext.setIntegQAContext(integQAContext);
        externalSignoffContext.setIntegQAContext(integQAContext);
        
        this.integQAContext = integQAContext;
        this.integTestrailContext = integTestrailContext;
        this.externalSignoffContext = externalSignoffContext;
        
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
    public void testEnd2End() throws Exception
    {
        GenericEventHandler<IntegProductQANewEvent> integProductQANewEventHandler = new GenericEventHandler<IntegProductQANewEvent>(IntegProductQANewEvent.class);
        GenericEventHandler<IntegBuildQANewEvent> integBuildQANewEventHandler = new GenericEventHandler<IntegBuildQANewEvent>(IntegBuildQANewEvent.class);
        GenericEventHandler<IntegTestrailProjectNewEvent> integTestrailProjectNewEventHandler = new GenericEventHandler<IntegTestrailProjectNewEvent>(IntegTestrailProjectNewEvent.class);
        GenericEventHandler<IntegTestrailProjectReadyEvent> integTestrailProjectReadyEventHandler = new GenericEventHandler<IntegTestrailProjectReadyEvent>(IntegTestrailProjectReadyEvent.class);
        GenericEventHandler<IntegTestrailPlanNewEvent> integTestrailPlanNewEventHandler = new GenericEventHandler<IntegTestrailPlanNewEvent>(IntegTestrailPlanNewEvent.class);
        
        demoSystem.getEventBus().registerEventHandler(IntegProductQANewEvent.class, integProductQANewEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegBuildQANewEvent.class, integBuildQANewEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegTestrailProjectNewEvent.class, integTestrailProjectNewEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegTestrailProjectReadyEvent.class, integTestrailProjectReadyEventHandler);
        demoSystem.getEventBus().registerEventHandler(IntegTestrailPlanNewEvent.class, integTestrailPlanNewEventHandler);
        
        Product product = new Product("Navy's Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        Entity<ProductQA> productQAEntity = qaSystem.getCommandBus().submit(new ProductQANewCommand(productEntity.getId())).getResult();
        productQAEntity = qaSystem.getCommandBus().submit(new ProductQAAddPlanCommand(productQAEntity.getId(), preflightPlan())).getResult();
        
        integProductQANewEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(integProductQANewEventHandler.getEvent());
        
        integTestrailProjectNewEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(integTestrailProjectNewEventHandler.getEvent());
        
        integTestrailProjectReadyEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(integTestrailProjectReadyEventHandler.getEvent());
        
        Entity<IntegTestrailProject> integTestrailProjectEntity = demoSystem.getCommandBus().submit(new IntegTestrailProjectGetCommand(integTestrailProjectReadyEventHandler.getEvent().getEntityId())).getResult();
        Long testrailProjectId = integTestrailProjectEntity.getData().getTestrailProjectId();
        System.out.println("testrailProjectId = "+testrailProjectId);
        
        Entity<IntegProductQA> integProductQAEntity = demoSystem.getCommandBus().submit(new IntegProductQAGetCommand(integTestrailProjectEntity.getData().getIntegProductQAInfo().getId())).getResult();
        System.out.println("integProductQAEntity.getData().getStatus() is "+integProductQAEntity.getData().getStatus());
        
        //clean up
        testrailSystem.getCommandBus().submit(new DeleteProjectCommand(testrailProjectId)).getResult();
    }
    
    private Plan preflightPlan()
    {        
        Plan p = new Plan("Preflight", "Test Run for ");
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.UK, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.CN, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.JP, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.DE, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.FR, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.IT, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.CA, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.ES, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.IN, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),Locale.BR, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Desktop JS Error",Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US under Chrome & IE11",Locale.US, Platform.Desktop, Browser.Any, View.Desktop, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"DE under FireFox",Locale.DE, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"JP under IE10",Locale.JP, Platform.Desktop, Browser.IE10, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CN under IE6 & IE8",Locale.CN, Platform.Desktop, Browser.Any, View.Desktop, Priority.P1));
        
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Phone Web JS Error",Locale.US, Platform.Any, Browser.Any, View.MobileWeb, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Phone Web",Locale.US, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"UK Phone Web",Locale.UK, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CN Phone Web",Locale.CN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"JP Phone Web",Locale.JP, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"DE Phone Web",Locale.DE, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"FR Phone Web",Locale.FR, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CN Phone Web",Locale.CN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CA Phone Web",Locale.CA, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"ES Phone Web",Locale.ES, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"IT Phone Web",Locale.IT, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"IN Phone Web",Locale.IN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"BR Phone Web",Locale.BR, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        
        
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Android Phone App",Locale.US, Platform.Any, Browser.App, View.MobileApp, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"All locales on tablet apps(Shared among locales, part on Android tablet app, part on IOS tablet app and the others on Kindle G6 and G5)",Locale.Any, Platform.Any, Browser.Any, View.TabletApp, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"Win 8 App - US",Locale.US, Platform.Any, Browser.Any, View.MobileApp, Priority.P0));   

        return p;
    }
    
    private PlanEntry makePlanEntry(String entryBaseName, String entryName, Locale locale, Platform platform, Browser browser, View view, Priority priority)
    {
        if(entryName == null)
        {
            entryName = String.format("%s %s %s %s %s", entryBaseName, view, platform, browser, locale);
        }
        return new PlanEntry(entryName, locale, platform, browser, view, priority);
    }
    
    private PlanEntry makePlanEntry(String entryBaseName, Locale locale, Platform platform, Browser browser, View view, Priority priority)
    {
        return makePlanEntry(entryBaseName, null, locale, platform, browser, view, priority);
    }
    
//    @Test
//    public void testIntegTestrailContext_Create() throws Exception
//    {
//        GenericEventHandler<IntegQAProjectCreatedEvent> integQAProjectCreatedEventHandler = new GenericEventHandler<IntegQAProjectCreatedEvent>(IntegQAProjectCreatedEvent.class);
//        
//        demoSystem.getEventBus().registerEventHandler(IntegQAProjectCreatedEvent.class, integQAProjectCreatedEventHandler);
//         
//        /**
//         * create product
//         */
//        Product pmProduct = new Product("product for demo purpose", "Amazon Shopping Cart");
//        CreateProductCommand createProductCommand = new CreateProductCommand(pmProduct.getName(), pmProduct.getDesc());
//        Entity<Product> pmProductEntity = pmSystem.getCommandBus().submit(createProductCommand).getResult();
//        
//        /**
//         * create project
//         */
//        Project qaProject = new Project(pmProductEntity, "Project for demo purpose");
//        CreateProjectCommand createProjectCommand = new CreateProjectCommand(qaProject.getName(), qaProject.getProductInfo().getId());
//        Entity<Project> qaProjectEntity = qaSystem.getCommandBus().submit(createProjectCommand).getResult();
//        
//        
//        /**
//         * create testrail project
//         */
//        JSONObject testrailProject = demoSystem.getCommandBus().submit(new AddTestrailProjectCommand(qaProjectEntity.getId())).getResult();
//        Assert.assertNotNull(testrailProject);
//        JSONObject testrailSuite = demoSystem.getCommandBus().submit(new AddTestrailSuiteCommand(qaProjectEntity.getId())).getResult();
//        Assert.assertNotNull(testrailSuite);
//        
//        /**
//         * add plan to project
//         */
//        Plan plan = new Plan("Test Plan for demo purpuse","Run for ");
//        PlanEntry planEntry = new PlanEntry("entry for demo", null, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1);
//        plan = plan.addEntry(planEntry);
//        AddPlanToProjectCommand addPlanToProjectCommand = new AddPlanToProjectCommand(qaProjectEntity.getId(), plan);
//        qaProjectEntity = qaSystem.getCommandBus().submit(addPlanToProjectCommand).getResult();
//        
//        /**
//         * delete the testrail project
//         */
//        testrailSystem.getCommandBus().submit(new DeleteProjectCommand((Long) testrailProject.get(TestrailAPI.Key.Id))).getResult();
//        
//        try {
//            testrailProject = testrailSystem.getCommandBus().submit(new GetProjectCommand((Long) testrailProject.get(TestrailAPI.Key.Id))).getResult();
//            Assert.fail("should not be here");
//        } catch (CommandException ex)
//        {
//            System.out.println("get expected exception");
//        }
//    }
}
