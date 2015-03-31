package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.MyTestrailProjectGetAllCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectGetAllCommandHandler extends AbsCommandHandler<MyTestrailProjectGetAllCommand, Set<Entity<MyTestrailProject>>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailProjectGetAllCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Set<Entity<MyTestrailProject>> handle(MyTestrailProjectGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<MyTestrailProject>> ret = integTestrailContext.findMyTestrailProject(new EntitySpec<MyTestrailProject>(){

                @Override
                public boolean matches(Entity<MyTestrailProject> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

