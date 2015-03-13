package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.AddTestSuiteCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class AddTestSuiteCommandHandler extends AbsCommandHandler<AddTestSuiteCommand, JSONObject>
{
    TestrailContext testrailContext;

    public AddTestSuiteCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(AddTestSuiteCommand command) throws CommandException
    {
        try {
            return testrailContext.addTestSuite(command.getProjectId(), command.getPostData());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
