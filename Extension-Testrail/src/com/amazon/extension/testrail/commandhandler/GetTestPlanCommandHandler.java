package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.GetTestPlanCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestPlanCommandHandler extends AbsCommandHandler<GetTestPlanCommand, JSONObject>
{
    TestrailContext testrailContext;
        
    public GetTestPlanCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetTestPlanCommand command) throws CommandException
    {
        try {
            return testrailContext.getTestPlan(command.getPlanId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
