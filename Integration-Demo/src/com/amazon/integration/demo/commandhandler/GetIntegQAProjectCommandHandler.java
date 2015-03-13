package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.GetIntegQAProjectCommand;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class GetIntegQAProjectCommandHandler extends AbsCommandHandler<GetIntegQAProjectCommand, Entity<IntegQAProject>>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegContext integContext;

    public GetIntegQAProjectCommandHandler(IntegContext integContext)
    {
        this.integContext = integContext;
    }
    
    @Override
    public Entity<IntegQAProject> handle(GetIntegQAProjectCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            Entity<IntegQAProject> integQAProject = integContext.loadIntegQAProject(command.getQaProjectId());
            logger.fine(String.format("Handler finished command[%s]",command));
            return integQAProject;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
