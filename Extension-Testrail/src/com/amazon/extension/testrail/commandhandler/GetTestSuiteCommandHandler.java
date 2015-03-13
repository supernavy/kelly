package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.GetTestSuiteCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestSuiteCommandHandler extends AbsCommandHandler<GetTestSuiteCommand, JSONObject>
{
    TestrailServiceContext testrailContext;
        
    public GetTestSuiteCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetTestSuiteCommand command) throws CommandException
    {
        try {
            return testrailContext.sendGet(TestrailAPI.Method.GET_SUITE, new Object[]{command.getSuiteId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
