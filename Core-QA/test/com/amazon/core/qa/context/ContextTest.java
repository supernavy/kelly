package com.amazon.core.qa.context;

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
import com.amazon.core.qa.context.impl.PlanRunContextImpl;
import com.amazon.core.qa.context.impl.ProjectContextImpl;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.core.qa.system.QASystem;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.core.rm.system.ReleaseSystemAssembler;
import com.amazon.core.rm.system.SimpleReleaseSystem;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;

public class ContextTest
{
    AppSystem pmSystem;
    AppSystem rmSystem;
    AppSystem qaSystem;
    ProjectContext projectContext;
    PlanRunContext planRunContext;

    @BeforeTest
    public void init() throws Exception
    {
        pmSystem = new SimpleProductSystem("PM System", Layer.Core);
        new ProductSystemAssembler().assemble(pmSystem);
        rmSystem = new SimpleReleaseSystem("RM System", Layer.Core, pmSystem);
        new ReleaseSystemAssembler().assemble(rmSystem);
        qaSystem = new SimpleQASystem("demo qa", Layer.Core, pmSystem, rmSystem);
        new QASystemAssembler().assemble(qaSystem);
        
        Repository<Project> rep = qaSystem.getRepository(QASystem.Repository_Project);
        projectContext = new ProjectContextImpl(qaSystem.getEventBus(), rep, qaSystem.getDependency(QASystem.System_PM).getCommandBus());
        Repository<PlanRun> planRunRepo = qaSystem.getRepository(QASystem.Repository_PlanRun);
        planRunContext = new PlanRunContextImpl(qaSystem.getEventBus(), planRunRepo, projectContext, qaSystem.getDependency(QASystem.System_RM).getCommandBus());
        
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
    public void testProjectContext() throws Exception
    {
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();
        
        Project project = new Project(productEntity, "cart qa project");

        Entity<Project> pEntity = projectContext.createProject(project.getName(), project.getProductInfo().getId());
        Assert.assertNotNull(pEntity);
        Assert.assertNotNull(pEntity.getId());
        Assert.assertNotNull(pEntity.getData());

        Assert.assertEquals(pEntity.getData().getName(), project.getName());
        Assert.assertEquals(pEntity.getData().getProductInfo().getId(), productEntity.getId());
    }
    
    @Test
    public void testPlanRunContext() throws Exception
    {
        Product product = new Product("Cart","Amazon Shopping Cart");
        Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(product.getName(), product.getDesc())).getResult();

        final Plan plan = new Plan("Preflight", "Test Run for ");
        Project project = new Project(productEntity, "Cart QA Project");
        Entity<Project> projectEntity = qaSystem.getCommandBus().submit(new CreateProjectCommand(project.getName(), project.getProductInfo().getId())).getResult();
        projectEntity = qaSystem.getCommandBus().submit(new AddPlanToProjectCommand(projectEntity.getId(), plan)).getResult();

        Build build = new Build(productEntity, "build X");
        Entity<Build> buildEntity = rmSystem.getCommandBus().submit(new CreateBuildCommand(build.getProductInfo().getId(), build.getBuildName(), build.getPatchName())).getResult();
        
        Assert.assertFalse(projectEntity.getData().getPlans().isEmpty());
        
        Entity<PlanRun> planRunEntity = planRunContext.createTestPlanRun(projectEntity.getId(), plan.getName(), buildEntity.getId());
        Assert.assertNotNull(planRunEntity);
        Assert.assertNotNull(planRunEntity.getId());
        
        Entity<PlanRun> loadedPlanRunEntity = planRunContext.load(planRunEntity.getId());
        Assert.assertEquals(loadedPlanRunEntity, planRunEntity);
    }
}
