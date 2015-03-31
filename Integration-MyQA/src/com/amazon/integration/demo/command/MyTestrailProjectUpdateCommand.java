package com.amazon.integration.demo.command;

import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public class MyTestrailProjectUpdateCommand extends AbsCommand<Entity<MyTestrailProject>>
{
    String integTestrailProjectId;
    MyTestrailProject myTestrailProject;
    public MyTestrailProjectUpdateCommand(String integTestrailProjectId, MyTestrailProject myTestrailProject)
    {
        this.integTestrailProjectId = integTestrailProjectId;
        this.myTestrailProject = myTestrailProject;
    }

    public String getMyTestrailProjectId()
    {
        return integTestrailProjectId;
    }
    
    public MyTestrailProject getMyTestrailProject()
    {
        return myTestrailProject;
    }
}
