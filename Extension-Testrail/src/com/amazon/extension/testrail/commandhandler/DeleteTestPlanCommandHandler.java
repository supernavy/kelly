package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.DeleteTestPlanCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class DeleteTestPlanCommandHandler extends AbsCommandHandler<DeleteTestPlanCommand, JSONObject>
{
    TestrailContext testrailContext;
        
    public DeleteTestPlanCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(DeleteTestPlanCommand command) throws CommandException
    {
        try {
            return testrailContext.deleteTestPlan(command.getPlanId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
