package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectInitCommand extends AbsCommand<Entity<MyTestrailProject>>
{
    String integTestrailProjectId;

    public MyTestrailProjectInitCommand(String integTestrailProjectId)
    {
        this.integTestrailProjectId = integTestrailProjectId;
    }

    public String getMyTestrailProjectId()
    {
        return integTestrailProjectId;
    }
}
