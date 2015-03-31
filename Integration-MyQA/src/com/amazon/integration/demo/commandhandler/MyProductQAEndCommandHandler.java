package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyProductQAEndCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAEndCommandHandler extends AbsCommandHandler<MyProductQAEndCommand, Entity<MyProductQA>>
{
    MyQAContext integQAContext;
    
    
    public MyProductQAEndCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyProductQA> handle(MyProductQAEndCommand command) throws CommandException
    {
        try {
            Entity<MyProductQA> ret = integQAContext.endMyProductQA(command.getMyProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
