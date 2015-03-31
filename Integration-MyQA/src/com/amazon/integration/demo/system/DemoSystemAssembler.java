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
import com.amazon.integration.demo.command.MyBuildQAEndCommand;
import com.amazon.integration.demo.command.MyBuildQAGetAllCommand;
import com.amazon.integration.demo.command.MyBuildQAGetCommand;
import com.amazon.integration.demo.command.MyBuildQANewCommand;
import com.amazon.integration.demo.command.MyBuildQAPrepareCommand;
import com.amazon.integration.demo.command.MyBuildQAUpdateTestrailPlanCommand;
import com.amazon.integration.demo.command.MyProductQAEndCommand;
import com.amazon.integration.demo.command.MyProductQAGetAllCommand;
import com.amazon.integration.demo.command.MyProductQAGetCommand;
import com.amazon.integration.demo.command.MyProductQANewCommand;
import com.amazon.integration.demo.command.MyProductQAPrepareCommand;
import com.amazon.integration.demo.command.MyProductQAUpdateTestrailProjectCommand;
import com.amazon.integration.demo.command.MyTestrailPlanEndCommand;
import com.amazon.integration.demo.command.MyTestrailPlanGetAllCommand;
import com.amazon.integration.demo.command.MyTestrailPlanGetCommand;
import com.amazon.integration.demo.command.MyTestrailPlanHandleTestrailPlanCompleteCommand;
import com.amazon.integration.demo.command.MyTestrailPlanInitCommand;
import com.amazon.integration.demo.command.MyTestrailPlanNewCommand;
import com.amazon.integration.demo.command.MyTestrailPlanStartCommand;
import com.amazon.integration.demo.command.MyTestrailPlanUpdateCommand;
import com.amazon.integration.demo.command.MyTestrailProjectEndCommand;
import com.amazon.integration.demo.command.MyTestrailProjectGetAllCommand;
import com.amazon.integration.demo.command.MyTestrailProjectGetCommand;
import com.amazon.integration.demo.command.MyTestrailProjectInitCommand;
import com.amazon.integration.demo.command.MyTestrailProjectNewCommand;
import com.amazon.integration.demo.command.MyTestrailProjectUpdateCommand;
import com.amazon.integration.demo.commandhandler.ExternalSignoffAssignCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffEndCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffGetCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffNewCommandHandler;
import com.amazon.integration.demo.commandhandler.ExternalSignoffSendRequestCommandHandler;
import com.amazon.integration.demo.commandhandler.MyBuildQAEndCommandHandler;
import com.amazon.integration.demo.commandhandler.MyBuildQAGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.MyBuildQAGetCommandHandler;
import com.amazon.integration.demo.commandhandler.MyBuildQANewCommandHandler;
import com.amazon.integration.demo.commandhandler.MyBuildQAPrepareCommandHandler;
import com.amazon.integration.demo.commandhandler.MyBuildQAUpdateTestrailPlanCommandHandler;
import com.amazon.integration.demo.commandhandler.MyProductQAEndCommandHandler;
import com.amazon.integration.demo.commandhandler.MyProductQAGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.MyProductQAGetCommandHandler;
import com.amazon.integration.demo.commandhandler.MyProductQANewCommandHandler;
import com.amazon.integration.demo.commandhandler.MyProductQAPrepareCommandHandler;
import com.amazon.integration.demo.commandhandler.MyProductQAUpdateTestrailProjectCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanEndCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanGetCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanHandleTestrailPlanCompleteCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanInitCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanNewCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanStartCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailPlanUpdateCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailProjectEndCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailProjectGetAllCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailProjectGetCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailProjectInitCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailProjectNewCommandHandler;
import com.amazon.integration.demo.commandhandler.MyTestrailProjectUpdateCommandHandler;
import com.amazon.integration.demo.context.impl.ExternalSignoffContextImpl;
import com.amazon.integration.demo.context.impl.MyQAContextImpl;
import com.amazon.integration.demo.context.impl.MyTestrailContextImpl;
import com.amazon.integration.demo.domain.event.MyBuildQAEndEvent;
import com.amazon.integration.demo.domain.event.MyBuildQANewEvent;
import com.amazon.integration.demo.domain.event.MyBuildQAPreparingEvent;
import com.amazon.integration.demo.domain.event.MyBuildQATestingEvent;
import com.amazon.integration.demo.domain.event.MyProductQANewEvent;
import com.amazon.integration.demo.domain.event.MyProductQAPreparingEvent;
import com.amazon.integration.demo.domain.event.MyTestrailPlanEndEvent;
import com.amazon.integration.demo.domain.event.MyTestrailPlanNewEvent;
import com.amazon.integration.demo.domain.event.MyTestrailPlanReadyEvent;
import com.amazon.integration.demo.domain.event.MyTestrailProjectNewEvent;
import com.amazon.integration.demo.domain.event.MyTestrailProjectReadyEvent;
import com.amazon.integration.demo.eventhandler.MyBuildQAEndEventHandler;
import com.amazon.integration.demo.eventhandler.MyBuildQANewEventHandler;
import com.amazon.integration.demo.eventhandler.MyBuildQAPreparingEventHandler;
import com.amazon.integration.demo.eventhandler.MyBuildQATestingEventHandler;
import com.amazon.integration.demo.eventhandler.MyProductQANewEventHandler;
import com.amazon.integration.demo.eventhandler.MyProductQAPreparingEventHandler;
import com.amazon.integration.demo.eventhandler.MyTestrailPlanEndEventHandler;
import com.amazon.integration.demo.eventhandler.MyTestrailPlanNewEventHandler;
import com.amazon.integration.demo.eventhandler.MyTestrailPlanReadyEventHandler;
import com.amazon.integration.demo.eventhandler.MyTestrailProjectNewEventHandler;
import com.amazon.integration.demo.eventhandler.MyTestrailProjectReadyEventHandler;
import com.amazon.integration.demo.eventhandler.qa.BuildQANewEventHandler;
import com.amazon.integration.demo.eventhandler.qa.ProductQANewEventHandler;
import com.amazon.integration.demo.eventhandler.testrail.TestPlanCompletedEventHandler;

public class DemoSystemAssembler implements AppSystemAssembler
{
    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {        
        MyQAContextImpl integQAContext = new MyQAContextImpl(system);
        MyTestrailContextImpl integTestrailContext = new MyTestrailContextImpl(system);
        ExternalSignoffContextImpl externalSignoffContext = new ExternalSignoffContextImpl(system);
        
        integQAContext.setMyTestrailContext(integTestrailContext);
        integTestrailContext.setMyQAContext(integQAContext);
        externalSignoffContext.setMyQAContext(integQAContext);
        
        AppSystem qaSystem = system.getDependency(DemoSystem.System_QA);
        qaSystem.getEventBus().registerEventHandler(ProductQANewEvent.class, new ProductQANewEventHandler(system.getCommandBus()));
        qaSystem.getEventBus().registerEventHandler(BuildQANewEvent.class, new BuildQANewEventHandler(system.getCommandBus()));
        
        AppSystem testrailSystem = system.getDependency(DemoSystem.System_Testrail);
        testrailSystem.getEventBus().registerEventHandler(TestPlanCompletedEvent.class, new TestPlanCompletedEventHandler(system.getCommandBus()));
        
        /**
         * Event handler registration
         */
        system.getEventBus().registerEventHandler(MyBuildQAEndEvent.class, new MyBuildQAEndEventHandler(system.getCommandBus(), qaSystem.getCommandBus()));
        system.getEventBus().registerEventHandler(MyBuildQANewEvent.class, new MyBuildQANewEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(MyBuildQAPreparingEvent.class, new MyBuildQAPreparingEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(MyBuildQATestingEvent.class, new MyBuildQATestingEventHandler(system));
        
        system.getEventBus().registerEventHandler(MyProductQAPreparingEvent.class, new MyProductQAPreparingEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(MyProductQANewEvent.class, new MyProductQANewEventHandler(system.getCommandBus()));
        
        system.getEventBus().registerEventHandler(MyTestrailPlanEndEvent.class, new MyTestrailPlanEndEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(MyTestrailPlanNewEvent.class, new MyTestrailPlanNewEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(MyTestrailPlanReadyEvent.class, new MyTestrailPlanReadyEventHandler(system.getCommandBus()));
        
        system.getEventBus().registerEventHandler(MyTestrailProjectNewEvent.class, new MyTestrailProjectNewEventHandler(system.getCommandBus()));
        system.getEventBus().registerEventHandler(MyTestrailProjectReadyEvent.class, new MyTestrailProjectReadyEventHandler(system.getCommandBus()));
                
        /**
         * Command handler registration
         */
        system.getCommandBus().register(MyProductQANewCommand.class, new MyProductQANewCommandHandler(integQAContext));
        system.getCommandBus().register(MyProductQAGetCommand.class, new MyProductQAGetCommandHandler(integQAContext));
        system.getCommandBus().register(MyProductQAGetAllCommand.class, new MyProductQAGetAllCommandHandler(integQAContext));
        system.getCommandBus().register(MyProductQAEndCommand.class, new MyProductQAEndCommandHandler(integQAContext));
        system.getCommandBus().register(MyProductQAPrepareCommand.class, new MyProductQAPrepareCommandHandler(integQAContext));
        system.getCommandBus().register(MyProductQAUpdateTestrailProjectCommand.class, new MyProductQAUpdateTestrailProjectCommandHandler(integQAContext));
        
        system.getCommandBus().register(MyBuildQANewCommand.class, new MyBuildQANewCommandHandler(integQAContext));
        system.getCommandBus().register(MyBuildQAGetCommand.class, new MyBuildQAGetCommandHandler(integQAContext));
        system.getCommandBus().register(MyBuildQAGetAllCommand.class, new MyBuildQAGetAllCommandHandler(integQAContext));
        system.getCommandBus().register(MyBuildQAEndCommand.class, new MyBuildQAEndCommandHandler(integQAContext));
        system.getCommandBus().register(MyBuildQAPrepareCommand.class, new MyBuildQAPrepareCommandHandler(integQAContext));
        system.getCommandBus().register(MyBuildQAUpdateTestrailPlanCommand.class, new MyBuildQAUpdateTestrailPlanCommandHandler(integQAContext));        
        
        system.getCommandBus().register(ExternalSignoffNewCommand.class, new ExternalSignoffNewCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffGetCommand.class, new ExternalSignoffGetCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffGetAllCommand.class, new ExternalSignoffGetAllCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffEndCommand.class, new ExternalSignoffEndCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffAssignCommand.class, new ExternalSignoffAssignCommandHandler(externalSignoffContext));
        system.getCommandBus().register(ExternalSignoffSendRequestCommand.class, new ExternalSignoffSendRequestCommandHandler(externalSignoffContext));
        
        system.getCommandBus().register(MyTestrailProjectNewCommand.class, new MyTestrailProjectNewCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailProjectGetCommand.class, new MyTestrailProjectGetCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailProjectGetAllCommand.class, new MyTestrailProjectGetAllCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailProjectEndCommand.class, new MyTestrailProjectEndCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailProjectInitCommand.class, new MyTestrailProjectInitCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailProjectUpdateCommand.class, new MyTestrailProjectUpdateCommandHandler(integTestrailContext));
        
        system.getCommandBus().register(MyTestrailPlanNewCommand.class, new MyTestrailPlanNewCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanGetCommand.class, new MyTestrailPlanGetCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanGetAllCommand.class, new MyTestrailPlanGetAllCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanEndCommand.class, new MyTestrailPlanEndCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanInitCommand.class, new MyTestrailPlanInitCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanUpdateCommand.class, new MyTestrailPlanUpdateCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanStartCommand.class, new MyTestrailPlanStartCommandHandler(integTestrailContext));
        system.getCommandBus().register(MyTestrailPlanHandleTestrailPlanCompleteCommand.class, new MyTestrailPlanHandleTestrailPlanCompleteCommandHandler(integTestrailContext));
    }

}
