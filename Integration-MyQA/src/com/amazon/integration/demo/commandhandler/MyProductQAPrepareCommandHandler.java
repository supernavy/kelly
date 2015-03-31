package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyProductQAPrepareCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAPrepareCommandHandler extends AbsCommandHandler<MyProductQAPrepareCommand, Entity<MyProductQA>>
{
    MyQAContext integQAContext;
    
    
    public MyProductQAPrepareCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyProductQA> handle(MyProductQAPrepareCommand command) throws CommandException
    {
        try {
            Entity<MyProductQA> ret = integQAContext.prepareMyProductQA(command.getMyProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
