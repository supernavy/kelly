package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.CreateTestCaseCommand;
import com.amazon.core.qa.context.TestCaseContext;
import com.amazon.core.qa.domain.entity.TestCase;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class CreateTestCaseCommandHandler extends AbsCommandHandler<CreateTestCaseCommand, Entity<TestCase>>
{
    TestCaseContext testCaseContext;
    
    public CreateTestCaseCommandHandler(TestCaseContext testCaseContext)
    {
        this.testCaseContext = testCaseContext;
    }

    @Override
    public Entity<TestCase> handle(CreateTestCaseCommand command) throws CommandException
    {
        try {
            return testCaseContext.createTestCase(command.getName(), command.getDesc(), command.getProductId());
        } catch (Exception e) {
            throw new CommandException(e);
        }
    } 
}
