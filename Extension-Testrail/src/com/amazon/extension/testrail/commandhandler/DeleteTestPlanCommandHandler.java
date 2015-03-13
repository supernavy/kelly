package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.DeleteTestPlanCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class DeleteTestPlanCommandHandler extends AbsCommandHandler<DeleteTestPlanCommand, JSONObject>
{
    TestrailServiceContext testrailContext;
        
    public DeleteTestPlanCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(DeleteTestPlanCommand command) throws CommandException
    {
        try {
            return testrailContext.sendPost(TestrailAPI.Method.DELETE_PLAN, new Object[]{command.getPlanId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
