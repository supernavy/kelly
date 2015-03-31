package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailProjectEndCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectEndCommandHandler extends AbsCommandHandler<MyTestrailProjectEndCommand, Entity<MyTestrailProject>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailProjectEndCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailProject> handle(MyTestrailProjectEndCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailProject> ret = integTestrailContext.endMyTestrailProject(command.getMyTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

