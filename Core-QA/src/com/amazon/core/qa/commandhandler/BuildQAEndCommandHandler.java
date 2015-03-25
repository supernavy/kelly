package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.BuildQAEndCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class BuildQAEndCommandHandler extends AbsCommandHandler<BuildQAEndCommand, Entity<BuildQA>>
{
    QAContext qaContext;
    
    public BuildQAEndCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<BuildQA> handle(BuildQAEndCommand command) throws CommandException
    {
        try {
            Entity<BuildQA> ret = qaContext.endBuildQA(command.getBuildQAId(), command.getResult());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
