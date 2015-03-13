package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.UpdateIntegQAProjectCommand;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class UpdateIntegQAProjectCommandHandler extends AbsCommandHandler<UpdateIntegQAProjectCommand, Entity<IntegQAProject>>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegContext integContext;

    public UpdateIntegQAProjectCommandHandler(IntegContext integContext)
    {
        this.integContext = integContext;
    }
    
    @Override
    public Entity<IntegQAProject> handle(UpdateIntegQAProjectCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            Entity<IntegQAProject> integQAProject = integContext.updateIntegQAProject(command.getQaProjectId(), command.getIntegQAProject());
            logger.fine(String.format("Handler finished command[%s]",command));
            return integQAProject;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
