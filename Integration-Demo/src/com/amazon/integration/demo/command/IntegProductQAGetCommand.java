package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAGetCommand extends AbsCommand<Entity<IntegProductQA>>
{
    String integProductQAId;

    public IntegProductQAGetCommand(String integProductQAId)
    {
        this.integProductQAId = integProductQAId;
    }
    public String getIntegProductQAId()
    {
        return integProductQAId;
    }
}
