package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.AddTestPlanCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class AddTestPlanCommandHandler extends AbsCommandHandler<AddTestPlanCommand, JSONObject>
{
    TestrailContext testrailContext;
        
    public AddTestPlanCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(AddTestPlanCommand command) throws CommandException
    {
        try {
            return testrailContext.addTestPlan(command.getProjectId(), command.getPostData());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
