package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.MyBuildQAGetAllCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAGetAllCommandHandler extends AbsCommandHandler<MyBuildQAGetAllCommand, Set<Entity<MyBuildQA>>>
{
    MyQAContext integQAContext;
    
    
    public MyBuildQAGetAllCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Set<Entity<MyBuildQA>> handle(MyBuildQAGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<MyBuildQA>> ret = integQAContext.findMyBuildQA(new EntitySpec<MyBuildQA>(){

                @Override
                public boolean matches(Entity<MyBuildQA> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
