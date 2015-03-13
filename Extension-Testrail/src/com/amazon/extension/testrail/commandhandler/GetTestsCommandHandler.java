package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONArray;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.GetTestsCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestsCommandHandler extends AbsCommandHandler<GetTestsCommand, JSONArray>
{
    TestrailServiceContext testrailContext;
        
    public GetTestsCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetTestsCommand command) throws CommandException
    {
        try {
            return testrailContext.sendGet(TestrailAPI.Method.GET_TESTS, new Object[]{command.getRunId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
