package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public class MyProductQAGetCommand extends AbsCommand<Entity<MyProductQA>>
{
    String integProductQAId;

    public MyProductQAGetCommand(String integProductQAId)
    {
        this.integProductQAId = integProductQAId;
    }
    public String getMyProductQAId()
    {
        return integProductQAId;
    }
}
