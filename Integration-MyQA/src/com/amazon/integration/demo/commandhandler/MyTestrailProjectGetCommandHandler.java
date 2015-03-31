package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailProjectGetCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectGetCommandHandler extends AbsCommandHandler<MyTestrailProjectGetCommand, Entity<MyTestrailProject>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailProjectGetCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailProject> handle(MyTestrailProjectGetCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailProject> ret = integTestrailContext.loadMyTestrailProject(command.getMyTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

