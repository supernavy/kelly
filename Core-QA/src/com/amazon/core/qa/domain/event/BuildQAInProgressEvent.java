package com.amazon.core.qa.domain.event;

public class BuildQAInProgressEvent extends QAEntityEvent
{
    public BuildQAInProgressEvent(String buildQAId)
    {
        super(buildQAId);
    }
}
