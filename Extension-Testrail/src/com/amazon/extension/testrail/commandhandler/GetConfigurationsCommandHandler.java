package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONArray;
import com.amazon.extension.testrail.command.GetConfigurationsCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetConfigurationsCommandHandler extends AbsCommandHandler<GetConfigurationsCommand, JSONArray>
{
    TestrailContext testrailContext;
        
    public GetConfigurationsCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetConfigurationsCommand command) throws CommandException
    {
        try {
            return testrailContext.getConfigurations(command.getProjectId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
