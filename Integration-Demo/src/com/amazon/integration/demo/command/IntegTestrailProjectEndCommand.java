package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectEndCommand extends AbsCommand<Entity<IntegTestrailProject>>
{
    String integTestrailProjectId;

    public IntegTestrailProjectEndCommand(String integTestrailProjectId)
    {
        this.integTestrailProjectId = integTestrailProjectId;
    }

    public String getIntegTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
