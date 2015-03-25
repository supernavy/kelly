package com.amazon.core.qa.system;

import com.amazon.core.pm.domain.event.ProductNewEvent;
import com.amazon.core.qa.command.BuildQAEndCommand;
import com.amazon.core.qa.command.BuildQAGetAllCommand;
import com.amazon.core.qa.command.BuildQAGetCommand;
import com.amazon.core.qa.command.BuildQANewCommand;
import com.amazon.core.qa.command.BuildQAStartCommand;
import com.amazon.core.qa.command.ProductQAAddPlanCommand;
import com.amazon.core.qa.command.ProductQAEndCommand;
import com.amazon.core.qa.command.ProductQAGetAllCommand;
import com.amazon.core.qa.command.ProductQAGetCommand;
import com.amazon.core.qa.command.ProductQANewCommand;
import com.amazon.core.qa.command.ProductQAReadyCommand;
import com.amazon.core.qa.commandhandler.BuildQAEndCommandHandler;
import com.amazon.core.qa.commandhandler.BuildQAGetAllCommandHandler;
import com.amazon.core.qa.commandhandler.BuildQAGetCommandHandler;
import com.amazon.core.qa.commandhandler.BuildQANewCommandHandler;
import com.amazon.core.qa.commandhandler.BuildQAStartCommandHandler;
import com.amazon.core.qa.commandhandler.ProductQAAddPlanCommandHandler;
import com.amazon.core.qa.commandhandler.ProductQAEndCommandHandler;
import com.amazon.core.qa.commandhandler.ProductQAGetAllCommandHandler;
import com.amazon.core.qa.commandhandler.ProductQAGetCommandHandler;
import com.amazon.core.qa.commandhandler.ProductQANewCommandHandler;
import com.amazon.core.qa.commandhandler.ProductQAReadyCommandHandler;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.context.impl.QAContextImpl;
import com.amazon.core.qa.eventhandler.pm.ProductNewEventHandler;
import com.amazon.core.qa.eventhandler.rm.BuildNewEventHandler;
import com.amazon.core.rm.domain.event.BuildNewEvent;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class QASystemAssembler implements AppSystemAssembler
{

    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {                
        QAContext qaContext = new QAContextImpl(system);
        
        AppSystem pmSystem = system.getDependency(QASystem.System_PM);
        AppSystem rmSystem = system.getDependency(QASystem.System_RM);
        
        pmSystem.getEventBus().registerEventHandler(ProductNewEvent.class, new ProductNewEventHandler(system.getCommandBus()));
        
        rmSystem.getEventBus().registerEventHandler(BuildNewEvent.class, new BuildNewEventHandler(system.getCommandBus()));
        
        system.getCommandBus().register(ProductQANewCommand.class, new ProductQANewCommandHandler(qaContext));
        system.getCommandBus().register(ProductQAGetCommand.class, new ProductQAGetCommandHandler(qaContext));
        system.getCommandBus().register(ProductQAGetAllCommand.class, new ProductQAGetAllCommandHandler(qaContext));
        system.getCommandBus().register(ProductQAReadyCommand.class, new ProductQAReadyCommandHandler(qaContext));
        system.getCommandBus().register(ProductQAEndCommand.class, new ProductQAEndCommandHandler(qaContext));
        system.getCommandBus().register(ProductQAAddPlanCommand.class, new ProductQAAddPlanCommandHandler(qaContext));
        
        system.getCommandBus().register(BuildQANewCommand.class, new BuildQANewCommandHandler(qaContext));
        system.getCommandBus().register(BuildQAGetCommand.class, new BuildQAGetCommandHandler(qaContext));
        system.getCommandBus().register(BuildQAGetAllCommand.class, new BuildQAGetAllCommandHandler(qaContext));
        system.getCommandBus().register(BuildQAStartCommand.class, new BuildQAStartCommandHandler(qaContext));
        system.getCommandBus().register(BuildQAEndCommand.class, new BuildQAEndCommandHandler(qaContext));
    }
}
