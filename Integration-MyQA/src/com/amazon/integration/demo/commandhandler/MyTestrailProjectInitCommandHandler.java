package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailProjectInitCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectInitCommandHandler extends AbsCommandHandler<MyTestrailProjectInitCommand, Entity<MyTestrailProject>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailProjectInitCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailProject> handle(MyTestrailProjectInitCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailProject> ret = integTestrailContext.initMyTestrailProject(command.getMyTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

