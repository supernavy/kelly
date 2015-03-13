package com.amazon.extension.testrail.command;

import org.json.simple.JSONObject;
import com.amazon.infra.commandbus.AbsCommand;

public class AddTestCaseCommand extends AbsCommand<JSONObject>
{
    Long sectionId;
    JSONObject postData;
    
    public AddTestCaseCommand(Long sectionId, JSONObject postData)
    {
        this.sectionId = sectionId;
        this.postData = postData;
    }
    public Long getSectionId()
    {
        return sectionId;
    }
    public JSONObject getPostData()
    {
        return postData;
    }
}
