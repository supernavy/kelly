package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.CreateTestSuiteCommand;
import com.amazon.core.qa.context.TestSuiteContext;
import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class CreateTestSuiteCommandHandler extends AbsCommandHandler<CreateTestSuiteCommand, Entity<TestSuite>>
{
    TestSuiteContext testSuiteContext;
    
    public CreateTestSuiteCommandHandler(TestSuiteContext testSuiteContext)
    {
        this.testSuiteContext = testSuiteContext;
    }

    @Override
    public Entity<TestSuite> handle(CreateTestSuiteCommand command) throws CommandException
    {
        try {
            return testSuiteContext.createTestSuite(command.getName(), command.getDesc(), command.getProjectId(), command.getTestCaseIds());
        } catch (Exception e) {
            throw new CommandException(e);
        }
    } 
}
