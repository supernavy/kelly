package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class BuildQAGetCommand extends AbsCommand<Entity<BuildQA>>
{
    String buildQAId;

    public BuildQAGetCommand(String buildQAId)
    {
        this.buildQAId = buildQAId;
    }
    public String getBuildQAId()
    {
        return buildQAId;
    }
}
