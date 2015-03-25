package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAEndCommand extends AbsCommand<Entity<IntegProductQA>>
{
    String integProductQAId;

    public IntegProductQAEndCommand(String integProductQAId)
    {
        this.integProductQAId = integProductQAId;
    }
    public String getIntegProductQAId()
    {
        return integProductQAId;
    }
}
