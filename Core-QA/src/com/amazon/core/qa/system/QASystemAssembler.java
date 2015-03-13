package com.amazon.core.qa.system;

import com.amazon.core.qa.command.AddPlanToProjectCommand;
import com.amazon.core.qa.command.CreatePlanRunCommand;
import com.amazon.core.qa.command.CreateProjectCommand;
import com.amazon.core.qa.command.CreateTestCaseCommand;
import com.amazon.core.qa.command.CreateTestSuiteCommand;
import com.amazon.core.qa.command.GetPlanRunCommand;
import com.amazon.core.qa.command.GetProjectCommand;
import com.amazon.core.qa.commandhandler.AddPlanToProjectCommandHandler;
import com.amazon.core.qa.commandhandler.CreatePlanRunCommandHandler;
import com.amazon.core.qa.commandhandler.CreateProjectCommandHandler;
import com.amazon.core.qa.commandhandler.CreateTestCaseCommandHandler;
import com.amazon.core.qa.commandhandler.CreateTestSuiteCommandHandler;
import com.amazon.core.qa.commandhandler.GetPlanRunCommandHandler;
import com.amazon.core.qa.commandhandler.GetProjectCommandHandler;
import com.amazon.core.qa.context.PlanRunContext;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.context.TestCaseContext;
import com.amazon.core.qa.context.TestSuiteContext;
import com.amazon.core.qa.context.impl.PlanRunContextImpl;
import com.amazon.core.qa.context.impl.ProjectContextImpl;
import com.amazon.core.qa.context.impl.TestCaseContextImpl;
import com.amazon.core.qa.context.impl.TestSuiteContextImpl;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.entity.TestCase;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class QASystemAssembler implements AppSystemAssembler
{

    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {
        Repository<Project> projectRepository = system.getRepository(QASystem.Repository_Project);
        Repository<PlanRun> planRunRepository = system.getRepository(QASystem.Repository_PlanRun);
        Repository<TestCase> testCaseRepository = system.getRepository(QASystem.Repository_TestCase);
        Repository<TestSuite> testSuiteRepository = system.getRepository(QASystem.Repository_TestSuite);
        
        AppSystem pmSystem = system.getDependency(QASystem.System_PM);
        AppSystem rmSystem = system.getDependency(QASystem.System_RM);
        
        ProjectContext projectContext = new ProjectContextImpl(system.getEventBus(), projectRepository, pmSystem.getCommandBus());
        PlanRunContext planRunContext = new PlanRunContextImpl(system.getEventBus(), planRunRepository, projectContext, rmSystem.getCommandBus());
        TestCaseContext testCaseContext = new TestCaseContextImpl(system.getEventBus(), testCaseRepository, pmSystem.getCommandBus());
        TestSuiteContext testSuiteContext = new TestSuiteContextImpl(system.getEventBus(), testSuiteRepository, projectContext, testCaseContext);
        
        
        system.getCommandBus().register(CreateProjectCommand.class, new CreateProjectCommandHandler(projectContext));
        system.getCommandBus().register(GetProjectCommand.class, new GetProjectCommandHandler(projectContext));
        system.getCommandBus().register(AddPlanToProjectCommand.class, new AddPlanToProjectCommandHandler(projectContext));
        
        system.getCommandBus().register(CreatePlanRunCommand.class, new CreatePlanRunCommandHandler(planRunContext));
        system.getCommandBus().register(GetPlanRunCommand.class, new GetPlanRunCommandHandler(planRunContext));
        
        system.getCommandBus().register(CreateTestSuiteCommand.class, new CreateTestSuiteCommandHandler(testSuiteContext));
        
        system.getCommandBus().register(CreateTestCaseCommand.class, new CreateTestCaseCommandHandler(testCaseContext));
    }
}
