package com.amazon.core.rm.command;

import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class CreateBuildCommand extends AbsCommand<Entity<Build>>
{
    String pmProductId;
    String buildName;
    String patchName;
    
    public CreateBuildCommand(String pmProductId, String buildName, String patchName)
    {
        this.pmProductId = pmProductId;
        this.buildName = buildName;
        this.patchName = patchName;
    }
    
    public String getPmProductId()
    {
        return pmProductId;
    }
    
    public String getBuildName()
    {
        return buildName;
    }
    
    public String getPatchName()
    {
        return patchName;
    }
}
