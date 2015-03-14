package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.UpdateTestSuiteCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class UpdateTestSuiteCommandHandler extends AbsCommandHandler<UpdateTestSuiteCommand, JSONObject>
{
    TestrailContext testrailContext;

    public UpdateTestSuiteCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(UpdateTestSuiteCommand command) throws CommandException
    {
        try {
            return testrailContext.updateTestSuite(command.getSuiteId(), command.getPostData());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
