package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;

public class GetIntegQAPlanRunCommand extends AbsCommand<Entity<IntegQAPlanRun>>
{
    String qaPlanRunId;
    public GetIntegQAPlanRunCommand(String qaPlanRunId)
    {
        this.qaPlanRunId = qaPlanRunId;
    }
    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }
}
