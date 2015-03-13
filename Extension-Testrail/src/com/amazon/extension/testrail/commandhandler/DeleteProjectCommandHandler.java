package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class DeleteProjectCommandHandler extends AbsCommandHandler<DeleteProjectCommand, JSONObject>
{
    TestrailServiceContext testrailContext;

    public DeleteProjectCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(DeleteProjectCommand command) throws CommandException
    {
        try {
            return testrailContext.sendPost(TestrailAPI.Method.DELETE_PROJECT, new Object[]{command.getProjectId()}, null);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
