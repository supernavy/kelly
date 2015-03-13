package com.amazon.integration.demo.system;

import com.amazon.core.qa.domain.event.ProjectPlanAddedEvent;
import com.amazon.core.qa.domain.event.PlanRunCreatedEvent;
import com.amazon.core.qa.domain.event.ProjectCreatedEvent;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;
import com.amazon.integration.demo.command.*;
import com.amazon.integration.demo.commandhandler.*;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.context.impl.IntegContextImpl;
import com.amazon.integration.demo.context.impl.IntegTestrailContextImpl;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;
import com.amazon.integration.demo.domain.entity.IntegQAProject;
import com.amazon.integration.demo.domain.entity.IntegQATestCase;
import com.amazon.integration.demo.domain.entity.IntegQATestSuite;
import com.amazon.integration.demo.eventhandler.qa.QAPlanRunCreatedEventHandler;
import com.amazon.integration.demo.eventhandler.qa.QAProjectCreatedEventHandler;
import com.amazon.integration.demo.eventhandler.qa.QAProjectPlanAddedEventHandler;

public class DemoSystemAssembler implements AppSystemAssembler
{
    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {        
        Repository<IntegQAProject> integQAProjectRepo = system.getRepository(DemoSystem.Repository_IntegQAProject);        
        Repository<IntegQAPlanRun> integQAPlanRunRepo = system.getRepository(DemoSystem.Repository_IntegQAPlanRun);
        Repository<IntegQATestSuite> integQATestSuiteRepo = system.getRepository(DemoSystem.Repository_IntegQATestSuite);        
        Repository<IntegQATestCase> integQATestCaseRepo = system.getRepository(DemoSystem.Repository_IntegQATestCase);
        
        AppSystem qaSystem = system.getDependency(DemoSystem.System_QA);
        AppSystem testrailSystem = system.getDependency(DemoSystem.System_Testrail);
//        CommandBus rmSystemCommandBus = system.getCommandBus(DemoSystem.CommandBus_RM);
//        CommandBus pmSystemCommandBus = system.getCommandBus(DemoSystem.CommandBus_PM);
        
        qaSystem.getEventBus().registerEventHandler(ProjectCreatedEvent.class, new QAProjectCreatedEventHandler(system.getCommandBus()));
        qaSystem.getEventBus().registerEventHandler(PlanRunCreatedEvent.class, new QAPlanRunCreatedEventHandler(system.getCommandBus()));
        qaSystem.getEventBus().registerEventHandler(ProjectPlanAddedEvent.class, new QAProjectPlanAddedEventHandler(system.getCommandBus()));
        
        IntegContext integContext = new IntegContextImpl(system.getEventBus(), integQAProjectRepo, integQAPlanRunRepo, integQATestSuiteRepo, integQATestCaseRepo);        
        IntegTestrailContext integTestrailContext = new IntegTestrailContextImpl(system.getEventBus(), integContext, qaSystem.getCommandBus(), testrailSystem.getCommandBus());
        
        system.getCommandBus().register(AddTestrailProjectCommand.class, new AddTestrailProjectCommandHandler(integTestrailContext));
        system.getCommandBus().register(AddTestrailPlanCommand.class, new AddTestrailPlanCommandHandler(integTestrailContext));
        system.getCommandBus().register(GetTestrailPlanCommand.class, new GetTestrailPlanCommandHandler(integTestrailContext));
        system.getCommandBus().register(AddTestrailResultForCaseCommand.class, new AddTestrailResultForCaseCommandHandler(integTestrailContext));
        system.getCommandBus().register(GetTestrailProjectCommand.class, new GetTestrailProjectCommandHandler(integTestrailContext));
        system.getCommandBus().register(AddTestrailSuiteCommand.class, new AddTestrailSuiteCommandHandler(integTestrailContext));
        
        system.getCommandBus().register(AddIntegQAProjectCommand.class, new AddIntegQAProjectCommandHandler(integContext));
        system.getCommandBus().register(UpdateIntegQAProjectCommand.class, new UpdateIntegQAProjectCommandHandler(integContext));
        system.getCommandBus().register(GetIntegQAProjectCommand.class, new GetIntegQAProjectCommandHandler(integContext));
        system.getCommandBus().register(AddPlanToIntegQAProjectCommand.class, new AddPlanToIntegQAProjectCommandHandler(integContext));
        system.getCommandBus().register(AddIntegQAPlanRunCommand.class, new AddIntegQAPlanRunCommandHandler(integContext));
        system.getCommandBus().register(GetIntegQAPlanRunCommand.class, new GetIntegQAPlanRunCommandHandler(integContext));
    }

}
