package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.IntegProductQAGetAllCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAGetAllCommandHandler extends AbsCommandHandler<IntegProductQAGetAllCommand, Set<Entity<IntegProductQA>>>
{
    IntegQAContext integQAContext;
    
    
    public IntegProductQAGetAllCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Set<Entity<IntegProductQA>> handle(IntegProductQAGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<IntegProductQA>> ret = integQAContext.findIntegProductQA(new EntitySpec<IntegProductQA>(){

                @Override
                public boolean matches(Entity<IntegProductQA> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
