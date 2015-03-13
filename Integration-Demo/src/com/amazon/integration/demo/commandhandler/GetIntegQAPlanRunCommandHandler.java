package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.GetIntegQAPlanRunCommand;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;

public class GetIntegQAPlanRunCommandHandler extends AbsCommandHandler<GetIntegQAPlanRunCommand, Entity<IntegQAPlanRun>>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegContext integContext;

    public GetIntegQAPlanRunCommandHandler(IntegContext integContext)
    {
        this.integContext = integContext;
    }
    
    @Override
    public Entity<IntegQAPlanRun> handle(GetIntegQAPlanRunCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            Entity<IntegQAPlanRun> integQAPlanRun = integContext.loadIntegQAPlanRun(command.getQaPlanRunId());
            logger.fine(String.format("Handler finished command[%s]",command));
            return integQAPlanRun;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
