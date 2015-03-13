package com.amazon.extension.testrail.command;

import org.json.simple.JSONArray;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestsCommand extends AbsCommand<JSONArray>
{
    Long runId;

    public GetTestsCommand(Long runId)
    {
        this.runId = runId;
    }
    public Long getRunId()
    {
        return runId;
    }
}
