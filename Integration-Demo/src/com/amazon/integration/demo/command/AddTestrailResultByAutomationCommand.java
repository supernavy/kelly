package com.amazon.integration.demo.command;

import org.json.simple.JSONArray;
import com.amazon.core.qa.domain.vo.planrun.TestCaseResult;
import com.amazon.infra.commandbus.AbsCommand;

public class AddTestrailResultByAutomationCommand extends AbsCommand<JSONArray>
{
    String qaPlanRunId;
    String qaProjectId;
    String qaProjectPlanName;
    String qaProjectPlanEntryName;
    String automationId;
    TestCaseResult result;

    public AddTestrailResultByAutomationCommand(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, String automationId, TestCaseResult result)
    {
        this.qaPlanRunId = qaPlanRunId;
        this.qaProjectId = qaProjectId;
        this.qaProjectPlanName = qaProjectPlanName;
        this.qaProjectPlanEntryName = qaProjectPlanEntryName;
        this.automationId = automationId;
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

    public String getAutomationId()
    {
        return automationId;
    }

    public TestCaseResult getResult()
    {
        return result;
    }
    
}
