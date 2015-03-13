package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddResultForCaseCommand extends AbsCommand<JSONObject>
{
    Long runId;
    Long caseId;
    JSONObject postData;
    public AddResultForCaseCommand(Long runId, Long caseId, JSONObject postData)
    {
        this.runId = runId;
        this.caseId = caseId;
        this.postData = postData;
    }
    public Long getRunId()
    {
        return runId;
    }
    public Long getCaseId()
    {
        return caseId;
    }
    public JSONObject getPostData()
    {
        return postData;
    }
    
}
