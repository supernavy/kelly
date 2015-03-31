package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.MyProductQAGetAllCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAGetAllCommandHandler extends AbsCommandHandler<MyProductQAGetAllCommand, Set<Entity<MyProductQA>>>
{
    MyQAContext integQAContext;
    
    
    public MyProductQAGetAllCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Set<Entity<MyProductQA>> handle(MyProductQAGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<MyProductQA>> ret = integQAContext.findMyProductQA(new EntitySpec<MyProductQA>(){

                @Override
                public boolean matches(Entity<MyProductQA> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
