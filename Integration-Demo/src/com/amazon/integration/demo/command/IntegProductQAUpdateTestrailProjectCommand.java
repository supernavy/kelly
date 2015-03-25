package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAUpdateTestrailProjectCommand extends AbsCommand<Entity<IntegProductQA>>
{
    String integProductQAId;
    String integTestrailProjectId;
    
    public IntegProductQAUpdateTestrailProjectCommand(String integProductQAId, String testrailProjectId)
    {
        this.integProductQAId = integProductQAId;
        this.integTestrailProjectId = testrailProjectId;
    }
    public String getIntegProductQAId()
    {
        return integProductQAId;
    }
    public String getTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
