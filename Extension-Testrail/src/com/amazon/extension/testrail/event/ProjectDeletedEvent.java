package com.amazon.extension.testrail.event;

public class ProjectDeletedEvent implements TestrailEvent
{
    Long projectId;

    public ProjectDeletedEvent(Long projectId)
    {
        this.projectId = projectId;
    }
    public Long getProjectId()
    {
        return projectId;
    }
}
