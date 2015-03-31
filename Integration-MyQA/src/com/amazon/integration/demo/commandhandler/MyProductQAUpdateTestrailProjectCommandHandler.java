package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyProductQAUpdateTestrailProjectCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAUpdateTestrailProjectCommandHandler extends AbsCommandHandler<MyProductQAUpdateTestrailProjectCommand, Entity<MyProductQA>>
{
    MyQAContext integQAContext;
    
    
    public MyProductQAUpdateTestrailProjectCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyProductQA> handle(MyProductQAUpdateTestrailProjectCommand command) throws CommandException
    {
        try {
            Entity<MyProductQA> ret = integQAContext.updateMyProductQAWithTestrailProjecct(command.getMyProductQAId(), command.getTestrailProjectId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
