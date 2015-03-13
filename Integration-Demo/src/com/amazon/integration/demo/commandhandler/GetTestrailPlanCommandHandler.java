package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.GetTestrailPlanCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class GetTestrailPlanCommandHandler extends AbsCommandHandler<GetTestrailPlanCommand, JSONObject>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext testrailContext;

    public GetTestrailPlanCommandHandler(IntegTestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetTestrailPlanCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]", command));
        try {
            JSONObject ret = testrailContext.loadTestrailPlan(command.getQaPlanRunId());
            logger.fine(String.format("Handler finished command[%s]", command));
            return ret;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

}
