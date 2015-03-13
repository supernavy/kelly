package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.command.AddProjectCommand;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class AddProjectCommandHandler extends AbsCommandHandler<AddProjectCommand, JSONObject>
{
    TestrailContext testrailContext;

    public AddProjectCommandHandler(TestrailContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(AddProjectCommand command) throws CommandException
    {
        try {
            return testrailContext.addProject(command.getPostData());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
