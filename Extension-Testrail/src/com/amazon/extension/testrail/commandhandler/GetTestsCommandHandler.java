package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONArray;
import com.amazon.extension.testrail.command.GetTestsCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestsCommandHandler extends AbsCommandHandler<GetTestsCommand, JSONArray>
{
    TestrailContext testrailContext;
        
    public GetTestsCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetTestsCommand command) throws CommandException
    {
        try {
            return testrailContext.getTests(command.getRunId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
