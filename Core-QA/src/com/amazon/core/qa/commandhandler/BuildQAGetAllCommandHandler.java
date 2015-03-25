package com.amazon.core.qa.commandhandler;

import java.util.Set;
import com.amazon.core.qa.command.BuildQAGetAllCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public class BuildQAGetAllCommandHandler extends AbsCommandHandler<BuildQAGetAllCommand, Set<Entity<BuildQA>>>
{
    QAContext qaContext;
    
    public BuildQAGetAllCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Set<Entity<BuildQA>> handle(BuildQAGetAllCommand command) throws CommandException
    {
        try {
            return qaContext.findBuildQA(new EntitySpec<BuildQA>(){

                @Override
                public boolean matches(Entity<BuildQA> entity)
                {
                   return true;
                }});
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
