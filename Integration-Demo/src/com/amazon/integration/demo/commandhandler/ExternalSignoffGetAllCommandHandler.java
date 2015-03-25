package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.ExternalSignoffGetAllCommand;
import com.amazon.integration.demo.context.ExternalSignoffContext;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffGetAllCommandHandler extends AbsCommandHandler<ExternalSignoffGetAllCommand, Set<Entity<ExternalSignoff>>>
{
    ExternalSignoffContext externalSignoffContext;
    
    
    public ExternalSignoffGetAllCommandHandler(ExternalSignoffContext externalSignoffContext)
    {
        this.externalSignoffContext = externalSignoffContext;
    }

    @Override
    public Set<Entity<ExternalSignoff>> handle(ExternalSignoffGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<ExternalSignoff>> ret = externalSignoffContext.findExternalSignoff(new EntitySpec<ExternalSignoff>(){

                @Override
                public boolean matches(Entity<ExternalSignoff> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
