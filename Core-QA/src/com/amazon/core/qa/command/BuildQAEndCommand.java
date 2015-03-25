package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.core.qa.domain.entity.BuildQA.Result;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class BuildQAEndCommand extends AbsCommand<Entity<BuildQA>>
{
    String buildQAId;
    Result result;

    public BuildQAEndCommand(String buildQAId, Result result)
    {
        this.buildQAId = buildQAId;
        this.result = result;
    }
    public String getBuildQAId()
    {
        return buildQAId;
    }
    public Result getResult()
    {
        return result;
    }
}
