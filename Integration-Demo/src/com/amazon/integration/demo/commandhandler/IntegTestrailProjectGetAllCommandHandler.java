package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.IntegTestrailProjectGetAllCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectGetAllCommandHandler extends AbsCommandHandler<IntegTestrailProjectGetAllCommand, Set<Entity<IntegTestrailProject>>>
{
    IntegTestrailContext integTestrailContext;
    
    
    public IntegTestrailProjectGetAllCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Set<Entity<IntegTestrailProject>> handle(IntegTestrailProjectGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<IntegTestrailProject>> ret = integTestrailContext.findIntegTestrailProject(new EntitySpec<IntegTestrailProject>(){

                @Override
                public boolean matches(Entity<IntegTestrailProject> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

