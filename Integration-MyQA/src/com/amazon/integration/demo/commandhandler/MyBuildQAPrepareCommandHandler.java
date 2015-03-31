package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyBuildQAPrepareCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAPrepareCommandHandler extends AbsCommandHandler<MyBuildQAPrepareCommand, Entity<MyBuildQA>>
{
    MyQAContext integQAContext;
    
    
    public MyBuildQAPrepareCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyBuildQA> handle(MyBuildQAPrepareCommand command) throws CommandException
    {
        try {
            Entity<MyBuildQA> ret = integQAContext.prepareMyBuildQA(command.getMyBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
