package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyTestrailProjectNewCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectNewCommandHandler extends AbsCommandHandler<MyTestrailProjectNewCommand, Entity<MyTestrailProject>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailProjectNewCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Entity<MyTestrailProject> handle(MyTestrailProjectNewCommand command) throws CommandException
    {
        try {
            Entity<MyTestrailProject> ret = integTestrailContext.newMyTestrailProject(command.getMyProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
