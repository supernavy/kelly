package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.AddTestrailSuiteCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class AddTestrailSuiteCommandHandler extends AbsCommandHandler<AddTestrailSuiteCommand, JSONObject>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext integTestrailContext;

    public AddTestrailSuiteCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }
    
    @Override
    public JSONObject handle(AddTestrailSuiteCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            JSONObject project = integTestrailContext.createTestrailTestSuite(command.getQaProjectId(), command.getPlanName());
            logger.fine(String.format("Handler finished command[%s]",command));
            return project;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

}
