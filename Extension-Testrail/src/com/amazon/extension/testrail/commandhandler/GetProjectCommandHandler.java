package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.GetProjectCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetProjectCommandHandler extends AbsCommandHandler<GetProjectCommand, JSONObject>
{
    TestrailContext testrailContext;
        
    public GetProjectCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONObject handle(GetProjectCommand command) throws CommandException
    {
        try {
            return testrailContext.getProject(command.getProjectId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
