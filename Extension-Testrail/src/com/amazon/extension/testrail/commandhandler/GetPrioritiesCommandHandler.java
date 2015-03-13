package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONArray;
import com.amazon.extension.testrail.command.GetPrioritiesCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetPrioritiesCommandHandler extends AbsCommandHandler<GetPrioritiesCommand, JSONArray>
{
    TestrailContext testrailContext;
        
    public GetPrioritiesCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetPrioritiesCommand command) throws CommandException
    {
        try {
            return testrailContext.getPriorities();
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
