package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class BuildQANewCommand extends AbsCommand<Entity<BuildQA>>
{
    String buildId;

    public BuildQANewCommand(String buildId)
    {
        this.buildId = buildId;
    }

    public String getBuildId()
    {
        return buildId;
    }
}
