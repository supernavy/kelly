package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectGetCommand extends AbsCommand<Entity<IntegTestrailProject>>
{
    String integTestrailProjectId;

    public IntegTestrailProjectGetCommand(String integTestrailProjectId)
    {
        this.integTestrailProjectId = integTestrailProjectId;
    }

    public String getIntegTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
