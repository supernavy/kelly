package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.AddIntegQAPlanRunCommand;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;

public class AddIntegQAPlanRunCommandHandler extends AbsCommandHandler<AddIntegQAPlanRunCommand, Entity<IntegQAPlanRun>>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegContext integContext;

    public AddIntegQAPlanRunCommandHandler(IntegContext integContext)
    {
        this.integContext = integContext;
    }
    
    @Override
    public Entity<IntegQAPlanRun> handle(AddIntegQAPlanRunCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            Entity<IntegQAPlanRun> integQAPlanRun = integContext.createIntegQAPlanRun(command.getQaPlanRunId(), command.getTestrailTestPlanId());
            logger.fine(String.format("Handler finished command[%s]",command));
            return integQAPlanRun;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
