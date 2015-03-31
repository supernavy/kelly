package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAUpdateTestrailProjectCommand extends AbsCommand<Entity<MyProductQA>>
{
    String integProductQAId;
    String integTestrailProjectId;
    
    public MyProductQAUpdateTestrailProjectCommand(String integProductQAId, String testrailProjectId)
    {
        this.integProductQAId = integProductQAId;
        this.integTestrailProjectId = testrailProjectId;
    }
    public String getMyProductQAId()
    {
        return integProductQAId;
    }
    public String getTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
