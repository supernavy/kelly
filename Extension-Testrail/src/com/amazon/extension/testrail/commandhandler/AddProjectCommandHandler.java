package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.AddProjectCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class AddProjectCommandHandler extends AbsCommandHandler<AddProjectCommand, JSONObject>
{
    TestrailServiceContext testrailContext;

    public AddProjectCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }


    @Override
    public JSONObject handle(AddProjectCommand command) throws CommandException
    {
        try {
            return testrailContext.sendPost(TestrailAPI.Method.ADD_PROJECT, new Object[]{}, command.getPostData());
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
