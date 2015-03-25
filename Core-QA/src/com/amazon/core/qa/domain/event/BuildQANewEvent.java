package com.amazon.core.qa.domain.event;

public class BuildQANewEvent extends QAEntityEvent
{
    public BuildQANewEvent(String buildQAId)
    {
        super(buildQAId);
    }
}
