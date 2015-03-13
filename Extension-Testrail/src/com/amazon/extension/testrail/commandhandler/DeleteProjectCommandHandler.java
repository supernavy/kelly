package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class DeleteProjectCommandHandler extends AbsCommandHandler<DeleteProjectCommand, JSONObject>
{
    TestrailContext testrailContext;

    public DeleteProjectCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(DeleteProjectCommand command) throws CommandException
    {
        try {
            return testrailContext.deleteProject(command.getProjectId());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
