package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.CreateProjectCommand;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class CreateProjectCommandHandler extends AbsCommandHandler<CreateProjectCommand, Entity<Project>>
{
    ProjectContext qaProjectContext;
    
    public CreateProjectCommandHandler(ProjectContext qaProjectContext)
    {
        this.qaProjectContext = qaProjectContext;
    }

    @Override
    public Entity<Project> handle(CreateProjectCommand command) throws CommandException
    {
        try {
            return qaProjectContext.createProject(command.getProjectName(), command.getProductId());
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
    
    
}
