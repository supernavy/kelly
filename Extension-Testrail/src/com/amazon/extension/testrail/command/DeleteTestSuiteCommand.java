package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class DeleteTestSuiteCommand extends AbsCommand<JSONObject>
{
    Long suiteId;
    
    public DeleteTestSuiteCommand(Long suiteId)
    {
        this.suiteId = suiteId;
    }
    
    public Long getSuiteId()
    {
        return suiteId;
    }
}
