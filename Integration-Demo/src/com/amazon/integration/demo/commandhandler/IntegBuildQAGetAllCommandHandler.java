package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.IntegBuildQAGetAllCommand;
import com.amazon.integration.demo.context.IntegQAContext;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQAGetAllCommandHandler extends AbsCommandHandler<IntegBuildQAGetAllCommand, Set<Entity<IntegBuildQA>>>
{
    IntegQAContext integQAContext;
    
    
    public IntegBuildQAGetAllCommandHandler(IntegQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Set<Entity<IntegBuildQA>> handle(IntegBuildQAGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<IntegBuildQA>> ret = integQAContext.findIntegBuildQA(new EntitySpec<IntegBuildQA>(){

                @Override
                public boolean matches(Entity<IntegBuildQA> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
