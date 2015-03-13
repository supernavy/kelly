package com.amazon.core.rm.commandhandler;

import com.amazon.core.rm.command.GetBuildCommand;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class GetBuildCommandHandler extends AbsCommandHandler<GetBuildCommand, Entity<Build>>
{
    BuildContext buildContext;
    
    public GetBuildCommandHandler(BuildContext buildContext)
    {
        this.buildContext = buildContext;
    }
    
    @Override
    public Entity<Build> handle(GetBuildCommand command) throws CommandException
    {
        try {
            return buildContext.loadBuild(command.getBuildId());
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

}
