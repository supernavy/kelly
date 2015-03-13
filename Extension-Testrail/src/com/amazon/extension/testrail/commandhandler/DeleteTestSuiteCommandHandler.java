package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.DeleteTestSuiteCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class DeleteTestSuiteCommandHandler extends AbsCommandHandler<DeleteTestSuiteCommand, JSONObject>
{
    TestrailContext testrailContext;

    public DeleteTestSuiteCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(DeleteTestSuiteCommand command) throws CommandException
    {
        try {
            return testrailContext.deleteTestSuite(command.getSuiteId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
