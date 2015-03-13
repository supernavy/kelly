package com.amazon.extension.testrail.command;

import org.json.simple.JSONArray;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestCasesCommand extends AbsCommand<JSONArray>
{
    Long projectId;
    Long suiteId;
    public GetTestCasesCommand(Long projectId, Long suiteId)
    {
        this.projectId = projectId;
        this.suiteId = suiteId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
    public Long getSuiteId()
    {
        return suiteId;
    }
}
