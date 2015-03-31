package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyProductQAGetCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAGetCommandHandler extends AbsCommandHandler<MyProductQAGetCommand, Entity<MyProductQA>>
{
    MyQAContext integQAContext;
    
    
    public MyProductQAGetCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyProductQA> handle(MyProductQAGetCommand command) throws CommandException
    {
        try {
            Entity<MyProductQA> ret = integQAContext.loadMyProductQA(command.getMyProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
