package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class GetTestCaseCommand extends AbsCommand<JSONObject>
{
    Long caseId;
    public GetTestCaseCommand(Long caseId)
    {
        this.caseId = caseId;
    }
    
    public Long getCaseId()
    {
        return caseId;
    }
}
