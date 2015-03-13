package com.amazon.core.qa.context;

import java.util.List;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.infra.context.AppContext;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public interface TestSuiteContext extends AppContext
{
    public Entity<TestSuite> createTestSuite(String name, String desc, String projectId, List<String> testCaseIds) throws AppContextException;
    public Entity<TestSuite> load(String id) throws AppContextException;
    public Entity<TestSuite> addTestCaseToTestSuite(String id, String testCaseId) throws AppContextException;
}
