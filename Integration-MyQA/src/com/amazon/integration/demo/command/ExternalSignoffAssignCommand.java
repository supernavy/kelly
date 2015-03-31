package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffAssignCommand extends AbsCommand<Entity<ExternalSignoff>>
{
    String externalSignoffId;
    ExternalSignoff.Owner owner;
    public ExternalSignoffAssignCommand(String externalSignoffId, ExternalSignoff.Owner owner)
    {
        this.externalSignoffId = externalSignoffId;
        this.owner = owner;
    }
    public String getExternalSignoffId()
    {
        return externalSignoffId;
    }
    public ExternalSignoff.Owner getOwner()
    {
        return owner;
    }
}
