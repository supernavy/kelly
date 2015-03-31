package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyBuildQANewCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQANewCommandHandler extends AbsCommandHandler<MyBuildQANewCommand, Entity<MyBuildQA>>
{
    MyQAContext integQAContext;
    
    
    public MyBuildQANewCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyBuildQA> handle(MyBuildQANewCommand command) throws CommandException
    {
        try {
            Entity<MyBuildQA> ret = integQAContext.newMyBuildQA(command.getBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
