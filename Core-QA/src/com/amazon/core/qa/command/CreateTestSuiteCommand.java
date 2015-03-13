package com.amazon.core.qa.command;

import java.util.ArrayList;
import java.util.List;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class CreateTestSuiteCommand extends AbsCommand<Entity<TestSuite>>
{
    String projectId;
    String name;
    String desc;
    List<String> testCaseIds = new ArrayList<String>();
    public CreateTestSuiteCommand(String projectId, String name, String desc, List<String> testCaseIds)
    {
        this.projectId = projectId;
        this.name = name;
        this.desc = desc;
        if(testCaseIds!=null)
        {
            this.testCaseIds.addAll(testCaseIds);
        }
    }
    public String getProjectId()
    {
        return projectId;
    }
    public String getName()
    {
        return name;
    }
    public List<String> getTestCaseIds()
    {
        return testCaseIds;
    }
    public String getDesc()
    {
        return desc;
    }
}
