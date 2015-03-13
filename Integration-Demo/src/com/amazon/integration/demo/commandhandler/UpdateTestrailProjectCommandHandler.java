package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.AddTestrailProjectCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class UpdateTestrailProjectCommandHandler extends AbsCommandHandler<AddTestrailProjectCommand, JSONObject>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext integTestrailContext;

    public UpdateTestrailProjectCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }
    
    @Override
    public JSONObject handle(AddTestrailProjectCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            JSONObject project = integTestrailContext.createTestrailProject(command.getQaProjectId());
            logger.fine(String.format("Handler finished command[%s]",command));
            return project;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
