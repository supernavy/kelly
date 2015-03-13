package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.AddTestrailPlanCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class AddTestrailPlanCommandHandler extends AbsCommandHandler<AddTestrailPlanCommand, JSONObject>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext integTestrailContext;

    public AddTestrailPlanCommandHandler(IntegTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public JSONObject handle(AddTestrailPlanCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]",command));
        try {
            JSONObject testPlan = integTestrailContext.createTestrailPlan(command.getQaPlanRunId());
            logger.fine(String.format("Handler finished command[%s]",command));
            return testPlan;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
