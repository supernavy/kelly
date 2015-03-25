package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectNewCommand extends AbsCommand<Entity<IntegTestrailProject>>
{
    String integProductQAId;

    public IntegTestrailProjectNewCommand(String integProductQAId)
    {
        this.integProductQAId = integProductQAId;
    }

    public String getIntegProductQAId()
    {
        return integProductQAId;
    }
}
