package com.amazon.core.qa.domain.entity;

import java.util.ArrayList;
import java.util.List;
import com.amazon.infra.domain.Entity;

public class TestSuite
{
    Entity<Project> projectInfo;
    
    String name;
    String desc;
    
    List<Entity<TestCase>> testCaseInfos = new ArrayList<Entity<TestCase>>();
    
    public TestSuite(String name, String desc, Entity<Project> projectInfo, List<Entity<TestCase>> testCaseInfos)
    {
        this.name = name;
        this.desc = desc;
        this.projectInfo = projectInfo;
        if(testCaseInfos!=null)
        {
            this.testCaseInfos.addAll(testCaseInfos);
        }
    }
    
    public List<Entity<TestCase>> getTestCaseInfos()
    {
        return new ArrayList<Entity<TestCase>>(testCaseInfos);
    }

    public TestSuite addTestCase(Entity<TestCase> testCaseInfo)
    {
        List<Entity<TestCase>> newTestCaseInfos = getTestCaseInfos();
        newTestCaseInfos.add(testCaseInfo);
        return new TestSuite(getName(), getDesc(), getProjectInfo(), newTestCaseInfos);
    }
    
    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public Entity<Project> getProjectInfo()
    {
        return projectInfo;
    }
}
