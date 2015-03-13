package com.amazon.core.qa.context.impl;

import java.util.ArrayList;
import java.util.List;
import com.amazon.core.qa.context.ProjectContext;
import com.amazon.core.qa.context.TestCaseContext;
import com.amazon.core.qa.context.TestSuiteContext;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.entity.TestCase;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class TestSuiteContextImpl extends AbsAppContextImpl implements TestSuiteContext
{
    Repository<TestSuite> testSuiteRepo;
    ProjectContext projectContext;
    TestCaseContext testCaseContext;
    
    public TestSuiteContextImpl(EventBus eventBus, Repository<TestSuite> testSuiteRepo, ProjectContext projectContext, TestCaseContext testCaseContext)
    {
        super(eventBus);
        this.testSuiteRepo = testSuiteRepo;
        this.projectContext = projectContext;
        this.testCaseContext = testCaseContext;
    }

    @Override
    public Entity<TestSuite> createTestSuite(String name, String desc, String projectId, List<String> testCaseIds) throws AppContextException
    {
        try {
            Entity<Project> projectInfo = projectContext.load(projectId);
            List<Entity<TestCase>> testCaseInfos = new ArrayList<Entity<TestCase>>();
            for(String testCaseId: testCaseIds)
            {
                testCaseInfos.add(testCaseContext.load(testCaseId));
            }
            return testSuiteRepo.createEntity(new TestSuite(name, desc, projectInfo, testCaseInfos));
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<TestSuite> load(String id) throws AppContextException
    {
        try {
            return testSuiteRepo.load(id);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public Entity<TestSuite> addTestCaseToTestSuite(String id, String testCaseId) throws AppContextException
    {
        try {
            Entity<TestCase> testCaseInfo = testCaseContext.load(testCaseId);
            Entity<TestSuite> testSuiteEntity = testSuiteRepo.load(id);
            TestSuite testSuite = testSuiteEntity.getData();
            testSuite = testSuite.addTestCase(testCaseInfo);
            return testSuiteRepo.updateEntity(testSuiteEntity.getId(), testSuite);
        } catch (RepositoryException e) {
            throw new AppContextException(e);
        }
    }

}
