package com.amazon.extension.testrail.domain.event;

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
