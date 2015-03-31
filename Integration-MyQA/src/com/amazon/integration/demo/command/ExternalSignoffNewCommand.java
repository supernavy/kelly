package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public class ExternalSignoffNewCommand extends AbsCommand<Entity<ExternalSignoff>>
{
    String integBuildQAId;
    String featureName;

    public ExternalSignoffNewCommand(String integBuildQAId, String featureName)
    {
        this.integBuildQAId = integBuildQAId;
        this.featureName = featureName;
    }
    public String getMyBuildQAId()
    {
        return integBuildQAId;
    }
    public String getFeatureName()
    {
        return featureName;
    }
}
