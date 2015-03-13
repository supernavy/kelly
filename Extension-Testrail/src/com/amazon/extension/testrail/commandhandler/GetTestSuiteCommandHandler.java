package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.GetTestSuiteCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestSuiteCommandHandler extends AbsCommandHandler<GetTestSuiteCommand, JSONObject>
{
    TestrailContext testrailContext;
        
    public GetTestSuiteCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetTestSuiteCommand command) throws CommandException
    {
        try {
            return testrailContext.getTestSuite(command.getSuiteId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
