package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONArray;
import com.amazon.extension.testrail.command.GetTestCasesCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestCasesCommandHandler extends AbsCommandHandler<GetTestCasesCommand, JSONArray>
{
    TestrailContext testrailContext;
        
    public GetTestCasesCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetTestCasesCommand command) throws CommandException
    {
        try {
            return testrailContext.getTestCases(command.getProjectId(), command.getSuiteId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
