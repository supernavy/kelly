package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.ExternalSignoffAssignCommand;
import com.amazon.integration.demo.context.ExternalSignoffContext;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffAssignCommandHandler extends AbsCommandHandler<ExternalSignoffAssignCommand, Entity<ExternalSignoff>>
{
    ExternalSignoffContext externalSignoffContext;
    
    
    public ExternalSignoffAssignCommandHandler(ExternalSignoffContext externalSignoffContext)
    {
        this.externalSignoffContext = externalSignoffContext;
    }

    @Override
    public Entity<ExternalSignoff> handle(ExternalSignoffAssignCommand command) throws CommandException
    {
        try {
            Entity<ExternalSignoff> ret = externalSignoffContext.assignExternalSignoff(command.getExternalSignoffId(), command.getOwner());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
