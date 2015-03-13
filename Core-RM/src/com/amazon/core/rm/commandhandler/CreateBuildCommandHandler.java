package com.amazon.core.rm.commandhandler;

import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class CreateBuildCommandHandler extends AbsCommandHandler<CreateBuildCommand, Entity<Build>>
{
    BuildContext buildContext;
    public CreateBuildCommandHandler(BuildContext buildContext)
    {
        this.buildContext = buildContext;
    }
    
    @Override
    public Entity<Build> handle(CreateBuildCommand command) throws CommandException
    {
        try {
            return buildContext.createBuild(command.getPmProductId(), command.getBuildName(), command.getPatchName());
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

}
