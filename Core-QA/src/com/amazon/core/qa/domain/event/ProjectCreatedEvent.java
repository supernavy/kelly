package com.amazon.core.qa.domain.event;

public class ProjectCreatedEvent implements QAEvent
{
    String projectId;
    
    public ProjectCreatedEvent(String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectId()
    {
        return projectId;
    }
}
