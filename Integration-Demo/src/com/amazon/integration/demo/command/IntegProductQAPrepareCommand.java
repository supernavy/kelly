package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public class IntegProductQAPrepareCommand extends AbsCommand<Entity<IntegProductQA>>
{
    String integProductQAId;

    public IntegProductQAPrepareCommand(String integProductQAId)
    {
        this.integProductQAId = integProductQAId;
    }
    public String getIntegProductQAId()
    {
        return integProductQAId;
    }
}
