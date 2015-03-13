package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONArray;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.AddTestrailResultByAutomationCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class AddTestrailResultByAutomationCommandHandler extends AbsCommandHandler<AddTestrailResultByAutomationCommand, JSONArray>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext testrailContext;

    public AddTestrailResultByAutomationCommandHandler(IntegTestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(AddTestrailResultByAutomationCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]", command));
        try {
            JSONArray savedResult = testrailContext.addResultForAutomation(command.getQaPlanRunId(), command.getQaProjectId(), command.getQaProjectPlanName(), command.getQaProjectPlanEntryName(), command.getAutomationId(), command.getResult());
            return savedResult;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
