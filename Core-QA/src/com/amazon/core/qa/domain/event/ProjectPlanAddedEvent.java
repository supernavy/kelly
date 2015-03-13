package com.amazon.core.qa.domain.event;

public class ProjectPlanAddedEvent implements QAEvent
{
    String projectId;
    String planName;
    public ProjectPlanAddedEvent(String projectId, String planName)
    {
        this.projectId = projectId;
        this.planName = planName;
    }
    public String getProjectId()
    {
        return projectId;
    }
    public String getPlanName()
    {
        return planName;
    }
}
