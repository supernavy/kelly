package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailProjectUpdateCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectUpdateCommandHandler extends AbsCommandHandler<MyTestrailProjectUpdateCommand, Entity<MyTestrailProject>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailProjectUpdateCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailProject> handle(MyTestrailProjectUpdateCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailProject> ret = integTestrailContext.updateMyTestrailProject(command.getMyTestrailProjectId(), command.getMyTestrailProject());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

