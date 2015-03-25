package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.ExternalSignoffGetCommand;
import com.amazon.integration.demo.context.ExternalSignoffContext;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffGetCommandHandler extends AbsCommandHandler<ExternalSignoffGetCommand, Entity<ExternalSignoff>>
{
    ExternalSignoffContext externalSignoffContext;
    
    
    public ExternalSignoffGetCommandHandler(ExternalSignoffContext externalSignoffContext)
    {
        this.externalSignoffContext = externalSignoffContext;
    }

    @Override
    public Entity<ExternalSignoff> handle(ExternalSignoffGetCommand command) throws CommandException
    {
        try {
            Entity<ExternalSignoff> ret = externalSignoffContext.loadExternalSignoff(command.getExternalSignoffId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
