package com.amazon.core.rm.commandhandler;

import java.util.Set;
import com.amazon.core.rm.command.GetBuildsAllCommand;
import com.amazon.core.rm.context.BuildContext;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public class GetBuildsAllCommandHandler extends AbsCommandHandler<GetBuildsAllCommand, Set<Entity<Build>>>
{
    BuildContext buildContext;
    
    public GetBuildsAllCommandHandler(BuildContext buildContext)
    {
        this.buildContext = buildContext;
    }
    
    @Override
    public Set<Entity<Build>> handle(GetBuildsAllCommand command) throws CommandException
    {
        try {
            return buildContext.findBuild(new EntitySpec<Build>(){
                @Override
                public boolean matches(Entity<Build> entity)
                {
                    return true;
                }});
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

}
