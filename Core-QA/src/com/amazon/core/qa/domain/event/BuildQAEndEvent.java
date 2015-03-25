package com.amazon.core.qa.domain.event;

public class BuildQAEndEvent extends QAEntityEvent
{
    public BuildQAEndEvent(String buildQAId)
    {
        super(buildQAId);
    }
}
