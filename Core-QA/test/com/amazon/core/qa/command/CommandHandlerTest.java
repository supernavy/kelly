package com.amazon.core.qa.command;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.system.PMSystemAssembler;
import com.amazon.core.pm.system.SimplePMSystem;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.event.BuildQANewEvent;
import com.amazon.core.qa.domain.event.ProductQANewEvent;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.core.qa.domain.vo.productqa.PlanEntry;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.RMSystemAssembler;
import com.amazon.core.rm.system.SimpleRMSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.GenericEventHandler;
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
        pmSystem = new SimplePMSystem("PM System", Layer.Core);
        new PMSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleRMSystem("RM System", Layer.Core, pmSystem);
        new RMSystemAssembler().assemble(rmSystem);
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
    public void testNewProductQA() throws Exception 
    {
        GenericEventHandler<ProductQANewEvent> productQANewEventHandler = new GenericEventHandler<ProductQANewEvent>(ProductQANewEvent.class);
        qaSystem.getEventBus().registerEventHandler(ProductQANewEvent.class, productQANewEventHandler);
        
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        ProductQA productQA = new ProductQA(productEntity);

        Entity<ProductQA> pEntity = qaSystem.getCommandBus().submit(new ProductQANewCommand(productEntity.getId())).getResult();
        Assert.assertNotNull(pEntity);
        Assert.assertNotNull(pEntity.getId());
        Assert.assertNotNull(pEntity.getData());
        Assert.assertEquals(pEntity.getData().getProductInfo().getId(), productQA.getProductInfo().getId());
        
        productQANewEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(productQANewEventHandler.getEvent());
        Assert.assertEquals(productQANewEventHandler.getEvent().getEntityId(), pEntity.getId());
    }
    
    @Test
    public void testNewBuildQA() throws Exception 
    {
        GenericEventHandler<BuildQANewEvent> buildQANewEventHandler = new GenericEventHandler<BuildQANewEvent>(BuildQANewEvent.class);
        qaSystem.getEventBus().registerEventHandler(BuildQANewEvent.class, buildQANewEventHandler);
        
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        Entity<ProductQA> productQAEntity = qaSystem.getCommandBus().submit(new ProductQANewCommand(productEntity.getId())).getResult();
        productQAEntity = qaSystem.getCommandBus().submit(new ProductQAAddPlanCommand(productQAEntity.getId(), preflightPlan())).getResult();
        
        Build build = new Build(productEntity, "Build X");
        Entity<Build> buildEntity = rmSystem.getCommandBus().submit(new CreateBuildCommand(productEntity.getId(), build.getBuildName(), build.getPatchName())).getResult();

        Entity<BuildQA> buildQAEntity = qaSystem.getCommandBus().submit(new BuildQANewCommand(buildEntity.getId())).getResult();
        Assert.assertNotNull(buildQAEntity);
        Assert.assertNotNull(buildQAEntity.getId());
        Assert.assertNotNull(buildQAEntity.getData());
        buildQANewEventHandler.waitUntilInvokedOrTimeout(10);
        Assert.assertNotNull(buildQANewEventHandler.getEvent());
        Assert.assertEquals(buildQANewEventHandler.getEvent().getEntityId(), buildQAEntity.getId());
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
}
