package com.amazon.core.rm.command;

import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class GetBuildCommand extends AbsCommand<Entity<Build>>
{
    String buildId;

    public GetBuildCommand(String buildId)
    {
        this.buildId = buildId;
    }

    public String getBuildId()
    {
        return buildId;
    }
}
