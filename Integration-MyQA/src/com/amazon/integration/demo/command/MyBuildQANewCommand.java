package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQANewCommand extends AbsCommand<Entity<MyBuildQA>>
{
    String buildQAId;

    public MyBuildQANewCommand(String buildQAId)
    {
        this.buildQAId = buildQAId;
    }
    public String getBuildQAId()
    {
        return buildQAId;
    }
}
