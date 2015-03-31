package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectNewCommand extends AbsCommand<Entity<MyTestrailProject>>
{
    String integProductQAId;

    public MyTestrailProjectNewCommand(String integProductQAId)
    {
        this.integProductQAId = integProductQAId;
    }

    public String getMyProductQAId()
    {
        return integProductQAId;
    }
}
