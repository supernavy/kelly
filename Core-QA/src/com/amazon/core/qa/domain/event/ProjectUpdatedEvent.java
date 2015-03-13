package com.amazon.core.qa.domain.event;

public class ProjectUpdatedEvent implements QAEvent
{
    String projectId;
    
    public ProjectUpdatedEvent(String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectId()
    {
        return projectId;
    }
}
