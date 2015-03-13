package com.amazon.integration.demo.command;

import org.json.simple.JSONObject;
import com.amazon.core.qa.domain.vo.planrun.TestCaseResult;
import com.amazon.infra.commandbus.AbsCommand;

public class AddTestrailResultForCaseCommand extends AbsCommand<JSONObject>
{
    String qaPlanRunId;
    String qaProjectId;
    String qaProjectPlanName;
    String qaProjectPlanEntryName;
    Long caseId;
    TestCaseResult result;

    public AddTestrailResultForCaseCommand(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, Long caseId, TestCaseResult result)
    {
        this.qaPlanRunId = qaPlanRunId;
        this.qaProjectId = qaProjectId;
        this.qaProjectPlanName = qaProjectPlanName;
        this.qaProjectPlanEntryName = qaProjectPlanEntryName;
        this.caseId = caseId;
        this.result = result;
    }

    public String getQaPlanRunId()
    {
        return qaPlanRunId;
    }

    public String getQaProjectId()
    {
        return qaProjectId;
    }

    public String getQaProjectPlanName()
    {
        return qaProjectPlanName;
    }

    public String getQaProjectPlanEntryName()
    {
        return qaProjectPlanEntryName;
    }

    public Long getCaseId()
    {
        return caseId;
    }

    public TestCaseResult getResult()
    {
        return result;
    }
    
}
