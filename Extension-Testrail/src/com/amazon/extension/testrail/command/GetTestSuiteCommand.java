package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestSuiteCommand extends AbsCommand<JSONObject>
{
    Long suiteId;
    public GetTestSuiteCommand(Long suiteId)
    {
        this.suiteId = suiteId;
    }
    public Long getSuiteId()
    {
        return suiteId;
    }
}
