package com.amazon.extension.testrail.event;

public class ProjectAddedEvent implements TestrailEvent
{
    Long projectId;

    public ProjectAddedEvent(Long projectId)
    {
        this.projectId = projectId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
}
