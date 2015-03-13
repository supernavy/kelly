package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.GetTestPlanCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestPlanCommandHandler extends AbsCommandHandler<GetTestPlanCommand, JSONObject>
{
    TestrailServiceContext testrailContext;
        
    public GetTestPlanCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetTestPlanCommand command) throws CommandException
    {
        try {
            return testrailContext.sendGet(TestrailAPI.Method.GET_PLAN, new Object[]{command.getPlanId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
