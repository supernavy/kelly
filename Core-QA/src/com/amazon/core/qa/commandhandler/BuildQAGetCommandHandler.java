package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.BuildQAGetCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class BuildQAGetCommandHandler extends AbsCommandHandler<BuildQAGetCommand, Entity<BuildQA>>
{
    QAContext qaContext;
    
    public BuildQAGetCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<BuildQA> handle(BuildQAGetCommand command) throws CommandException
    {
        try {
            Entity<BuildQA> ret = qaContext.loadBuildQA(command.getBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
