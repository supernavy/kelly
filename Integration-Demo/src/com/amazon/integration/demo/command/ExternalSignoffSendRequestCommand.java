package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffSendRequestCommand extends AbsCommand<Entity<ExternalSignoff>>
{
    String externalSignoffId;
    public ExternalSignoffSendRequestCommand(String externalSignoffId)
    {
        this.externalSignoffId = externalSignoffId;
    }
    public String getExternalSignoffId()
    {
        return externalSignoffId;
    }
}
