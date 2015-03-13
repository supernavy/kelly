package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.AddPlanToIntegQAProjectCommand;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.domain.entity.IntegQAProject;

public class AddPlanToIntegQAProjectCommandHandler extends AbsCommandHandler<AddPlanToIntegQAProjectCommand, Entity<IntegQAProject>>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegContext integContext;

    public AddPlanToIntegQAProjectCommandHandler(IntegContext integContext)
    {
        this.integContext = integContext;
    }
    
    @Override
    public Entity<IntegQAProject> handle(AddPlanToIntegQAProjectCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            Entity<IntegQAProject> integQAProject = integContext.addPlanToIntegQAProject(command.getQaProjectId(), command.getPlanName(), command.getTestrailSuiteId());
            logger.fine(String.format("Handler finished command[%s]",command));
            return integQAProject;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
