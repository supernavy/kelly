package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.BuildQANewCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class BuildQANewCommandHandler extends AbsCommandHandler<BuildQANewCommand, Entity<BuildQA>>
{
    QAContext qaContext;
    
    public BuildQANewCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<BuildQA> handle(BuildQANewCommand command) throws CommandException
    {
        try {
            Entity<BuildQA> ret = qaContext.newBuildQA(command.getBuildId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
