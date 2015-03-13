package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.DeleteTestSuiteCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class DeleteTestSuiteCommandHandler extends AbsCommandHandler<DeleteTestSuiteCommand, JSONObject>
{
    TestrailServiceContext testrailContext;

    public DeleteTestSuiteCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(DeleteTestSuiteCommand command) throws CommandException
    {
        try {
            return testrailContext.sendPost(TestrailAPI.Method.DELETE_SUITE, new Object[]{command.getSuiteId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
