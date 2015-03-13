package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.AddPlanToProjectCommand;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public class AddPlanToProjectCommandHandler extends AbsCommandHandler<AddPlanToProjectCommand, Entity<Project>>
{

    ProjectContext qaProjectContext;
    public AddPlanToProjectCommandHandler(ProjectContext qaProjectContext)
    {
        this.qaProjectContext = qaProjectContext;
    }
    
    @Override
    public Entity<Project> handle(AddPlanToProjectCommand command) throws CommandException
    {
        try {
            return qaProjectContext.addPlanToProject(command.getProjectId(), command.getPlan());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
