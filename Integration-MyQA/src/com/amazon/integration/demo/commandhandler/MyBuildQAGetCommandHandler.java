package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyBuildQAGetCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAGetCommandHandler extends AbsCommandHandler<MyBuildQAGetCommand, Entity<MyBuildQA>>
{
    MyQAContext integQAContext;
    
    
    public MyBuildQAGetCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyBuildQA> handle(MyBuildQAGetCommand command) throws CommandException
    {
        try {
            Entity<MyBuildQA> ret = integQAContext.loadMyBuildQA(command.getMyBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
