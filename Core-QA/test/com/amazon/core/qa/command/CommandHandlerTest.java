package com.amazon.core.qa.command;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.ProductSystemAssembler;
import com.amazon.core.pm.system.SimpleProductSystem;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.core.qa.domain.vo.project.PlanEntry;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.ReleaseSystemAssembler;
import com.amazon.core.rm.system.SimpleReleaseSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;

public class CommandHandlerTest
{
    AppSystem pmSystem;
    AppSystem rmSystem;
    AppSystem qaSystem;

    @BeforeTest
    public void init() throws Exception
    {
        pmSystem = new SimpleProductSystem("PM System", Layer.Core);
        new ProductSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleReleaseSystem("RM System", Layer.Core, pmSystem);
        new ReleaseSystemAssembler().assemble(rmSystem);
        qaSystem = new SimpleQASystem("demo qa", Layer.Core, pmSystem, rmSystem);
        new QASystemAssembler().assemble(qaSystem);
        
        pmSystem.start();
        rmSystem.start();
        qaSystem.start();
    }

    @AfterTest
    public void cleanUp() throws Exception
    {
        qaSystem.shutdown();
        rmSystem.shutdown();
        pmSystem.shutdown();
    }
    
    @Test
    public void testCreateProject() throws Exception 
    {
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        Project project = new Project(productEntity, "cart qa project");
        
        CreateProjectCommand com = new CreateProjectCommand(project.getName(), project.getProductInfo().getId());
        Entity<Project> pEntity = qaSystem.getCommandBus().submit(com).getResult();
        Assert.assertNotNull(pEntity);
        Assert.assertNotNull(pEntity.getId());
        Assert.assertNotNull(pEntity.getData());
        
        Assert.assertEquals(pEntity.getData().getName(), project.getName());
        Assert.assertEquals(pEntity.getData().getProductInfo().getId(), productEntity.getId());
    }
    
    @Test
    public void testCreatePlanRun() throws Exception 
    {
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();

        final Plan plan = preflightPlan(null);
        Project project = new Project(productEntity, "Cart QA Project");
        Entity<Project> projectEntity = qaSystem.getCommandBus().submit(new CreateProjectCommand(project.getName(), project.getProductInfo().getId())).getResult();
        projectEntity = qaSystem.getCommandBus().submit(new AddPlanToProjectCommand(projectEntity.getId(), plan)).getResult();

        Build build = new Build(productEntity, "build X");
        Entity<Build> buildEntity = rmSystem.getCommandBus().submit(new CreateBuildCommand(build.getProductInfo().getId(), build.getBuildName(), build.getPatchName())).getResult();
     

        Assert.assertFalse(projectEntity.getData().getPlans().isEmpty());
        
        CreatePlanRunCommand createPlanRunCommand = new CreatePlanRunCommand(projectEntity.getId(), plan.getName(), buildEntity.getId());
        Entity<PlanRun> planRunEntity = qaSystem.getCommandBus().submit(createPlanRunCommand).getResult();
        Assert.assertNotNull(planRunEntity);
        Assert.assertNotNull(planRunEntity.getId());
        
        GetPlanRunCommand getPlanRunCommand = new GetPlanRunCommand(planRunEntity.getId());
        Entity<PlanRun> loadedPlanRunEntity = qaSystem.getCommandBus().submit(getPlanRunCommand).getResult();
        Assert.assertEquals(loadedPlanRunEntity, planRunEntity);
    }

    private Plan preflightPlan(Entity<TestSuite> testSuiteInfo)
    {        
        Plan p = new Plan("Preflight", "Test Run for ");
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.UK, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.CN, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.JP, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.DE, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.FR, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.IT, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.CA, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.ES, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.IN, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),testSuiteInfo,Locale.BR, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Desktop JS Error",testSuiteInfo, Locale.US, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US under Chrome & IE11",testSuiteInfo, Locale.US, Platform.Desktop, Browser.Any, View.Desktop, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"DE under FireFox",testSuiteInfo, Locale.DE, Platform.Desktop, Browser.Firefox, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"JP under IE10",testSuiteInfo, Locale.JP, Platform.Desktop, Browser.IE10, View.Desktop, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CN under IE6 & IE8",testSuiteInfo, Locale.CN, Platform.Desktop, Browser.Any, View.Desktop, Priority.P1));
        
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Phone Web JS Error",testSuiteInfo, Locale.US, Platform.Any, Browser.Any, View.MobileWeb, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Phone Web",testSuiteInfo, Locale.US, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"UK Phone Web",testSuiteInfo, Locale.UK, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CN Phone Web",testSuiteInfo, Locale.CN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"JP Phone Web",testSuiteInfo, Locale.JP, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"DE Phone Web",testSuiteInfo, Locale.DE, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"FR Phone Web",testSuiteInfo, Locale.FR, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CN Phone Web",testSuiteInfo, Locale.CN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"CA Phone Web",testSuiteInfo, Locale.CA, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"ES Phone Web",testSuiteInfo, Locale.ES, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"IT Phone Web",testSuiteInfo, Locale.IT, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"IN Phone Web",testSuiteInfo, Locale.IN, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"BR Phone Web",testSuiteInfo, Locale.BR, Platform.Any, Browser.Safari, View.MobileWeb, Priority.P1));
        
        
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"US Android Phone App",testSuiteInfo, Locale.US, Platform.Any, Browser.App, View.MobileApp, Priority.P1));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"All locales on tablet apps(Shared among locales, part on Android tablet app, part on IOS tablet app and the others on Kindle G6 and G5)",testSuiteInfo, Locale.Any, Platform.Any, Browser.Any, View.TabletApp, Priority.P0));
        p.addEntry(makePlanEntry(p.getEntryBaseName(),"Win 8 App - US",testSuiteInfo, Locale.US, Platform.Any, Browser.Any, View.MobileApp, Priority.P0));   

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
