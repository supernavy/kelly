package com.amazon.integration.demo.commandhandler;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.integration.demo.command.GetTestrailProjectCommand;
import com.amazon.integration.demo.context.IntegTestrailContext;

public class GetTestrailProjectCommandHandler extends AbsCommandHandler<GetTestrailProjectCommand, JSONObject>
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegTestrailContext testrailContext;

    public GetTestrailProjectCommandHandler(IntegTestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetTestrailProjectCommand command) throws CommandException
    {
        logger.fine(String.format("Handler received command[%s]", command));
        try {
            JSONObject ret = testrailContext.loadTestrailProject(command.getQaProjectId());
            logger.fine(String.format("Handler finished command[%s]", command));
            return ret;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

}
