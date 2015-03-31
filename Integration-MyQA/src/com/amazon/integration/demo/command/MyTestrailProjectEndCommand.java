package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectEndCommand extends AbsCommand<Entity<MyTestrailProject>>
{
    String integTestrailProjectId;

    public MyTestrailProjectEndCommand(String integTestrailProjectId)
    {
        this.integTestrailProjectId = integTestrailProjectId;
    }

    public String getMyTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
