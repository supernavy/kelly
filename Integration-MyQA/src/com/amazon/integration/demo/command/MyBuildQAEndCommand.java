package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAEndCommand extends AbsCommand<Entity<MyBuildQA>>
{
    String integBuildQAId;

    public MyBuildQAEndCommand(String integBuildQAId)
    {
        this.integBuildQAId = integBuildQAId;
    }
    public String getMyBuildQAId()
    {
        return integBuildQAId;
    }
}
