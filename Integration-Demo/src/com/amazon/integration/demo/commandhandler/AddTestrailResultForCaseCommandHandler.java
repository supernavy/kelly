package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.AddTestrailResultForCaseCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class AddTestrailResultForCaseCommandHandler extends AbsCommandHandler<AddTestrailResultForCaseCommand, JSONObject>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext testrailContext;

    public AddTestrailResultForCaseCommandHandler(IntegTestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(AddTestrailResultForCaseCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]", command));
        try {
            JSONObject savedResult = testrailContext.addResultForCase(command.getQaPlanRunId(),command.getQaProjectId(), command.getQaProjectPlanName(), command.getQaProjectPlanEntryName(), command.getCaseId(), command.getResult());
            return savedResult;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
