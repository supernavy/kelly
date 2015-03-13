package com.amazon.extension.testrail.commandhandler;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.GetTestCasesCommand;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;

public class GetTestCasesCommandHandler extends AbsCommandHandler<GetTestCasesCommand, JSONArray>
{
    TestrailServiceContext testrailContext;
        
    public GetTestCasesCommandHandler(TestrailServiceContext testrailContext)
    {
        this.testrailContext = testrailContext;
    }

    @Override
    public JSONArray handle(GetTestCasesCommand command) throws CommandException
    {
        try {
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put(TestrailAPI.Key.Suite_Id, command.getSuiteId());
            return testrailContext.sendGet(TestrailAPI.Method.GET_CASES, new Object[]{command.getProjectId()}, filter);
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
}
