package com.amazon.extension.testrail.command;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddResultsForCasesCommand extends AbsCommand<JSONArray>
{
    Long runId;
    JSONObject postData;
    public AddResultsForCasesCommand(Long runId, JSONObject postData)
    {
        this.runId = runId;
        this.postData = postData;
    }
    public Long getRunId()
    {
        return runId;
    }
    public JSONObject getPostData()
    {
        return postData;
    }
    
}
