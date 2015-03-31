package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.ExternalSignoffNewCommand;
import com.amazon.integration.demo.context.ExternalSignoffContext;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffNewCommandHandler extends AbsCommandHandler<ExternalSignoffNewCommand, Entity<ExternalSignoff>>
{
    ExternalSignoffContext externalSignoffContext;
    
    
    public ExternalSignoffNewCommandHandler(ExternalSignoffContext externalSignoffContext)
    {
        this.externalSignoffContext = externalSignoffContext;
    }

    @Override
    public Entity<ExternalSignoff> handle(ExternalSignoffNewCommand command) throws CommandException
    {
        try {
            Entity<ExternalSignoff> ret = externalSignoffContext.newExternalSignoff(command.getMyBuildQAId(), command.getFeatureName());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
