package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;
import com.amazon.integration.demo.domain.entity.ExternalSignoff.Result;

public class ExternalSignoffEndCommand extends AbsCommand<Entity<ExternalSignoff>>
{
    String externalSignoffId;
    ExternalSignoff.Result result;
    public ExternalSignoffEndCommand(String externalSignoffId, Result result)
    {
        this.externalSignoffId = externalSignoffId;
        this.result = result;
    }
    public String getExternalSignoffId()
    {
        return externalSignoffId;
    }
    public ExternalSignoff.Result getResult()
    {
        return result;
    }
}
