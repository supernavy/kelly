package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyProductQANewCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQANewCommandHandler extends AbsCommandHandler<MyProductQANewCommand, Entity<MyProductQA>>
{
    MyQAContext integQAContext;
    
    
    public MyProductQANewCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyProductQA> handle(MyProductQANewCommand command) throws CommandException
    {
        try {
            Entity<MyProductQA> ret = integQAContext.newMyProductQA(command.getProductQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
