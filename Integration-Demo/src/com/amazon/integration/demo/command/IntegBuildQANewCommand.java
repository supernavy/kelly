package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;

public class IntegBuildQANewCommand extends AbsCommand<Entity<IntegBuildQA>>
{
    String buildQAId;

    public IntegBuildQANewCommand(String buildQAId)
    {
        this.buildQAId = buildQAId;
    }
    public String getBuildQAId()
    {
        return buildQAId;
    }
}
