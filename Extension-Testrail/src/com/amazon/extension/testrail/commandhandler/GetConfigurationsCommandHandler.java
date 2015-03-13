package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONArray;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.GetConfigurationsCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetConfigurationsCommandHandler extends AbsCommandHandler<GetConfigurationsCommand, JSONArray>
{
    TestrailServiceContext testrailContext;
        
    public GetConfigurationsCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetConfigurationsCommand command) throws CommandException
    {
        try {
            return testrailContext.sendGet(TestrailAPI.Method.GET_CONFIGURATIONS, new Object[]{command.getProjectId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
