package com.amazon.integration.demo.system;

import com.amazon.core.qa.domain.event.BuildQANewEvent;
import com.amazon.core.qa.domain.event.ProductQANewEvent;
import com.amazon.extension.testrail.domain.event.TestPlanCompletedEvent;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;
import com.amazon.integration.demo.command.ExternalSignoffAssignCommand;
import com.amazon.integration.demo.command.ExternalSignoffEndCommand;
import com.amazon.integration.demo.command.ExternalSignoffGetAllCommand;
import com.amazon.integration.demo.command.ExternalSignoffGetCommand;
import com.amazon.integration.demo.command.ExternalSignoffNewCommand;
import com.amazon.integration.demo.command.ExternalSignoffSendRequestCommand;
import com.amazon.integration.demo.command.IntegBuildQAEndCommand;
import com.amazon.integration.demo.command.IntegBuildQAGetAllCommand;
import com.amazon.integration.demo.command.IntegBuildQAGetCommand;
import com.amazon.integration.demo.command.IntegBuildQANewCommand;
import com.amazon.integration.demo.command.IntegBuildQAPrepareCommand;
import com.amazon.integration.demo.command.IntegBuildQAUpdateTestrailPlanCommand;
import com.amazon.integration.demo.command.IntegProductQAEndCommand;
import com.amazon.integration.demo.command.IntegProductQAGetAllCommand;
import com.amazon.integration.demo.command.IntegProductQAGetCommand;
import com.amazon.integration.demo.command.IntegProductQANewCommand;
import com.amazon.integration.demo.command.IntegProductQAPrepareCommand;
import com.amazon.integration.demo.command.IntegProductQAUpdateTestrailProjectCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanEndCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanGetAllCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanGetCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanHandleTestrailPlanCompleteCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanInitCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanNewCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanStartCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectEndCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectGetAllCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectGetCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectInitCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectNewCommand;
import com.amazon.integration.demo.commandhandler.ExternalSignoffAssignCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffEndCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffGetCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffNewCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffSendRequestCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegBuildQAEndCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegBuildQAGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegBuildQAGetCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegBuildQANewCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegBuildQAPrepareCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegBuildQAUpdateTestrailPlanCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegProductQAEndCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegProductQAGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegProductQAGetCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegProductQANewCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegProductQAPrepareCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegProductQAUpdateTestrailProjectCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanEndCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanGetCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanHandleTestrailPlanCompleteCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanInitCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanNewCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailPlanStartCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailProjectEndCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailProjectGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailProjectGetCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailProjectInitCommandHandler;
import com.amazon.integration.demo.commandhandler.IntegTestrailProjectNewCommandHandler;
import com.amazon.integration.demo.context.impl.ExternalSignoffContextImpl;
import com.amazon.integration.demo.context.impl.IntegQAContextImpl;
import com.amazon.integration.demo.context.impl.IntegTestrailContextImpl;
import com.amazon.integration.demo.domain.event.IntegBuildQAEndEvent;
import com.amazon.integration.demo.domain.event.IntegBuildQANewEvent;
import com.amazon.integration.demo.domain.event.IntegBuildQAPreparingEvent;
import com.amazon.integration.demo.domain.event.IntegBuildQATestingEvent;
import com.amazon.integration.demo.domain.event.IntegProductQANewEvent;
import com.amazon.integration.demo.domain.event.IntegProductQAPreparingEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanEndEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanNewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailPlanReadyEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectNewEvent;
import com.amazon.integration.demo.domain.event.IntegTestrailProjectReadyEvent;
import com.amazon.integration.demo.eventhandler.IntegBuildQAEndEventHandler;
import com.amazon.integration.demo.eventhandler.IntegBuildQANewEventHandler;
import com.amazon.integration.demo.eventhandler.IntegBuildQAPreparingEventHandler;
import com.amazon.integration.demo.eventhandler.IntegBuildQATestingEventHandler;
import com.amazon.integration.demo.eventhandler.IntegProductQANewEventHandler;
import com.amazon.integration.demo.eventhandler.IntegProductQAPreparingEventHandler;
import com.amazon.integration.demo.eventhandler.IntegTestrailPlanEndEventHandler;
import com.amazon.integration.demo.eventhandler.IntegTestrailPlanNewEventHandler;
import com.amazon.integration.demo.eventhandler.IntegTestrailPlanReadyEventHandler;
import com.amazon.integration.demo.eventhandler.IntegTestrailProjectNewEventHandler;
import com.amazon.integration.demo.eventhandler.IntegTestrailProjectReadyEventHandler;
import com.amazon.integration.demo.eventhandler.qa.BuildQANewEventHandler;
import com.amazon.integration.demo.eventhandler.qa.ProductQANewEventHandler;
import com.amazon.integration.demo.eventhandler.testrail.TestPlanCompletedEventHandler;

public class DemoSystemAssembler implements AppSystemAssembler
{
    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {        
        IntegQAContextImpl integQAContext = new IntegQAContextImpl(system);
        IntegTestrailContextImpl integTestrailContext = new IntegTestrailContextImpl(system);
        ExternalSignoffContextImpl externalSignoffContext = new ExternalSignoffContextImpl(system);
        
        integQAContext.setIntegTestrailContext(integTestrailContext);
        integTestrailContext.setIntegQAContext(integQAContext);
        externalSignoffContext.setIntegQAContext(integQAContext);
        
        AppSystem qaSystem = system.getDependency(DemoSystem.System_QA);
        qaSystem.getEventBus().registerEventHandler(ProductQANewEvent.class, new ProductQANewEventHandler(system.getCommandBus()));
        qaSystem.getEventBus().registerEventHandler(BuildQANewEvent.class, new BuildQANewEventHandler(system.getCommandBus()));
        
        AppSystem testrailSystem = system.getDependency(DemoSystem.System_Testrail);
        testrailSystem.getEventBus().registerEventHandler(TestPlanCompletedEvent.class, new TestPlanCompletedEventHandler(system.getCommandBus()));
        
        /**
         * Event handler registration
         */
        system.getEventBus().registerEventHandler(IntegBuildQAEndEvent.class, new IntegBuildQAEndEventHandler(system.getCommandBus(), qaSystem.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegBuildQANewEvent.class, new IntegBuildQANewEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegBuildQAPreparingEvent.class, new IntegBuildQAPreparingEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegBuildQATestingEvent.class, new IntegBuildQATestingEventHandler(system.getCommandBus()));
        
        system.getEventBus().registerEventHandler(IntegProductQAPreparingEvent.class, new IntegProductQAPreparingEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegProductQANewEvent.class, new IntegProductQANewEventHandler(system.getCommandBus()));
        
        system.getEventBus().registerEventHandler(IntegTestrailPlanEndEvent.class, new IntegTestrailPlanEndEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegTestrailPlanNewEvent.class, new IntegTestrailPlanNewEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegTestrailPlanReadyEvent.class, new IntegTestrailPlanReadyEventHandler(system.getCommandBus()));
        
        system.getEventBus().registerEventHandler(IntegTestrailProjectNewEvent.class, new IntegTestrailProjectNewEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(IntegTestrailProjectReadyEvent.class, new IntegTestrailProjectReadyEventHandler(system.getCommandBus()));
                
        /**
         * Command handler registration
         */
        system.getCommandBus().register(IntegProductQANewCommand.class, new IntegProductQANewCommandHandler(integQAContext));
        system.getCommandBus().register(IntegProductQAGetCommand.class, new IntegProductQAGetCommandHandler(integQAContext));
        system.getCommandBus().register(IntegProductQAGetAllCommand.class, new IntegProductQAGetAllCommandHandler(integQAContext));
        system.getCommandBus().register(IntegProductQAEndCommand.class, new IntegProductQAEndCommandHandler(integQAContext));
        system.getCommandBus().register(IntegProductQAPrepareCommand.class, new IntegProductQAPrepareCommandHandler(integQAContext));
        system.getCommandBus().register(IntegProductQAUpdateTestrailProjectCommand.class, new IntegProductQAUpdateTestrailProjectCommandHandler(integQAContext));
        
        system.getCommandBus().register(IntegBuildQANewCommand.class, new IntegBuildQANewCommandHandler(integQAContext));
        system.getCommandBus().register(IntegBuildQAGetCommand.class, new IntegBuildQAGetCommandHandler(integQAContext));
        system.getCommandBus().register(IntegBuildQAGetAllCommand.class, new IntegBuildQAGetAllCommandHandler(integQAContext));
        system.getCommandBus().register(IntegBuildQAEndCommand.class, new IntegBuildQAEndCommandHandler(integQAContext));
        system.getCommandBus().register(IntegBuildQAPrepareCommand.class, new IntegBuildQAPrepareCommandHandler(integQAContext));
        system.getCommandBus().register(IntegBuildQAUpdateTestrailPlanCommand.class, new IntegBuildQAUpdateTestrailPlanCommandHandler(integQAContext));
        
        system.getCommandBus().register(ExternalSignoffNewCommand.class, new ExternalSignoffNewCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffGetCommand.class, new ExternalSignoffGetCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffGetAllCommand.class, new ExternalSignoffGetAllCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffEndCommand.class, new ExternalSignoffEndCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffAssignCommand.class, new ExternalSignoffAssignCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffSendRequestCommand.class, new ExternalSignoffSendRequestCommandHandler(externalSignoffContext));
        
        system.getCommandBus().register(IntegTestrailProjectNewCommand.class, new IntegTestrailProjectNewCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailProjectGetCommand.class, new IntegTestrailProjectGetCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailProjectGetAllCommand.class, new IntegTestrailProjectGetAllCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailProjectEndCommand.class, new IntegTestrailProjectEndCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailProjectInitCommand.class, new IntegTestrailProjectInitCommandHandler(integTestrailContext));
        
        system.getCommandBus().register(IntegTestrailPlanNewCommand.class, new IntegTestrailPlanNewCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailPlanGetCommand.class, new IntegTestrailPlanGetCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailPlanGetAllCommand.class, new IntegTestrailPlanGetAllCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailPlanEndCommand.class, new IntegTestrailPlanEndCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailPlanInitCommand.class, new IntegTestrailPlanInitCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailPlanStartCommand.class, new IntegTestrailPlanStartCommandHandler(integTestrailContext));
        system.getCommandBus().register(IntegTestrailPlanHandleTestrailPlanCompleteCommand.class, new IntegTestrailPlanHandleTestrailPlanCompleteCommandHandler(integTestrailContext));
    }

}
