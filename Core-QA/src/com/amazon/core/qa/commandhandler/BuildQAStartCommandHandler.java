package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.BuildQAStartCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class BuildQAStartCommandHandler extends AbsCommandHandler<BuildQAStartCommand, Entity<BuildQA>>
{
    QAContext qaContext;
    
    public BuildQAStartCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Entity<BuildQA> handle(BuildQAStartCommand command) throws CommandException
    {
        try {
            Entity<BuildQA> ret = qaContext.startBuildQA(command.getBuildQAId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
