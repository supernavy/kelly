package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.GetProjectCommand;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class GetProjectCommandHandler extends AbsProjectCommandHandler<GetProjectCommand, Entity<Project>>
{   
    public GetProjectCommandHandler(ProjectContext context)
    { 
        super(context);
    }

    @Override
    public Entity<Project> handle(GetProjectCommand command) throws CommandException
    {
        try {
            return getContext().load(command.getProjectId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(e);
        }
    }

}
