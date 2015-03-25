package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class BuildQAStartCommand extends AbsCommand<Entity<BuildQA>>
{
    String buildQAId;

    public BuildQAStartCommand(String buildQAId)
    {
        this.buildQAId = buildQAId;
    }
    public String getBuildQAId()
    {
        return buildQAId;
    }
}
