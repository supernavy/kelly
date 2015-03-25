package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQAGetCommand extends AbsCommand<Entity<IntegBuildQA>>
{
    String integBuildQAId;

    public IntegBuildQAGetCommand(String integBuildQAId)
    {
        this.integBuildQAId = integBuildQAId;
    }
    public String getIntegBuildQAId()
    {
        return integBuildQAId;
    }
}
