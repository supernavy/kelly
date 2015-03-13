package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.AddResultForCaseCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class AddResultForCaseCommandHandler extends AbsCommandHandler<AddResultForCaseCommand, JSONObject>
{
    TestrailContext testrailContext;
    
    public AddResultForCaseCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }
    
    @Override
    public JSONObject handle(AddResultForCaseCommand command) throws CommandException
    {
        try {
            return testrailContext.addResultForCase(command.getRunId(), command.getCaseId(), command.getPostData());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
