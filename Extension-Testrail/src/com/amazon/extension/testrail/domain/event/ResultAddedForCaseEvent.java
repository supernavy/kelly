package com.amazon.extension.testrail.domain.event;

public class ResultAddedForCaseEvent implements TestrailEvent
{
    Long runId;
    Long caseId;
    
    public ResultAddedForCaseEvent(Long runId, Long caseId)
    {
        this.runId = runId;
        this.caseId = caseId;
    }

    public Long getRunId()
    {
        return runId;
    }
    
    public Long getCaseId()
    {
        return caseId;
    }
}
