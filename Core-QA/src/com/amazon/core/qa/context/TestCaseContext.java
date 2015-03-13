package com.amazon.core.qa.context;

import com.amazon.core.qa.domain.entity.TestCase;
import com.amazon.infra.context.AppContext;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public interface TestCaseContext extends AppContext
{
    public Entity<TestCase> createTestCase(String name, String desc, String productId) throws AppContextException;
    public Entity<TestCase> load(String id) throws AppContextException;
}
