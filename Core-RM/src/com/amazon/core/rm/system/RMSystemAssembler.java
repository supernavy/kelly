package com.amazon.core.rm.system;

import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.command.GetBuildCommand;
import com.amazon.core.rm.command.GetBuildsAllCommand;
import com.amazon.core.rm.commandhandler.CreateBuildCommandHandler;
import com.amazon.core.rm.commandhandler.GetBuildCommandHandler;
import com.amazon.core.rm.commandhandler.GetBuildsAllCommandHandler;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.context.impl.BuildContextImpl;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class RMSystemAssembler implements AppSystemAssembler
{

    @Override
    public void assemble(AppSystem appSystem) throws AppSystemException
    {        
        BuildContext buildContext = new BuildContextImpl(appSystem);
        //create and register command handler
        appSystem.getCommandBus().register(CreateBuildCommand.class, new CreateBuildCommandHandler(buildContext));
        appSystem.getCommandBus().register(GetBuildCommand.class, new GetBuildCommandHandler(buildContext));
        appSystem.getCommandBus().register(GetBuildsAllCommand.class, new GetBuildsAllCommandHandler(buildContext));
        //create and register event handler
    }
}
