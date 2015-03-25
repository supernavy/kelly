package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectInitCommand extends AbsCommand<Entity<IntegTestrailProject>>
{
    String integTestrailProjectId;

    public IntegTestrailProjectInitCommand(String integTestrailProjectId)
    {
        this.integTestrailProjectId = integTestrailProjectId;
    }

    public String getIntegTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
