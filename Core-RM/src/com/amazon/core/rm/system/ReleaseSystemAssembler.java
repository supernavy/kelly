package com.amazon.core.rm.system;

import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.command.GetBuildCommand;
import com.amazon.core.rm.commandhandler.CreateBuildCommandHandler;
import com.amazon.core.rm.commandhandler.GetBuildCommandHandler;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.context.impl.BuildContextImpl;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class ReleaseSystemAssembler implements AppSystemAssembler
{

    @Override
    public void assemble(AppSystem appSystem) throws AppSystemException
    {
        //initiate context
        Repository<Build> buildRepository = appSystem.getRepository(ReleaseSystem.Repository_Build);
        
        CommandBus pmSystemCommandBus = appSystem.getDependency(ReleaseSystem.System_PM).getCommandBus();
        
        BuildContext buildContext = new BuildContextImpl(appSystem.getEventBus(), buildRepository, pmSystemCommandBus);
        //create and register command handler
        appSystem.getCommandBus().register(CreateBuildCommand.class, new CreateBuildCommandHandler(buildContext));
        appSystem.getCommandBus().register(GetBuildCommand.class, new GetBuildCommandHandler(buildContext));
        //create and register event handler
    }
}
