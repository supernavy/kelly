package com.amazon.infra.commandbus;

import org.testng.Assert;
import com.amazon.infra.commandbus.Command;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.commandbus.CommandExecution;
import com.amazon.infra.commandbus.CommandHandler;

public class CommandBusTest
{

    public void testCommand(CommandBus bus) throws Exception
    {
        bus.start();
        MyCommand command = new MyCommand(1);
        bus.send(command);

        bus.stop();
    }

    public void testCommandTimeout(CommandBus bus) throws Exception
    {
        bus.start();
        MyCommand command = new MyCommand(1);
        CommandResult<Integer> result = bus.submit(command);
        try {
            result.getResult();
            Assert.fail("should not be here");
        } catch (CommandException ee) {
            ee.printStackTrace();
        }
        
        finally {
            bus.stop();
        }
    }

    public void testCommandHandler(CommandBus bus) throws Exception
    {
        bus.start();
        MyCommandHandler commandHandler = new MyCommandHandler();
        bus.register(MyCommand.class, commandHandler);


        MyCommand command1 = new MyCommand(1);
        MyCommand command2 = new MyCommand(2);
        MyCommand2 subTypeComamnd = new MyCommand2(3);
        CommandResult<Integer> result1 = bus.submit(command1);
        CommandResult<Integer> result2 = bus.submit(command2);
        CommandResult<Integer> result3 = bus.submit(subTypeComamnd);

        Assert.assertTrue(command1.getReturnType().cast(result1.getResult()).equals(1));
        Assert.assertTrue(command1.getReturnType().cast(result2.getResult()).equals(2));
        Assert.assertTrue(command1.getReturnType().cast(result3.getResult()).equals(3));
    }

    public void testRegisterCommandHandlerAfterCommand(CommandBus bus) throws Exception
    {
        bus.start();
        MyCommandHandler commandHandler = new MyCommandHandler();
        MyCommand command = new MyCommand(1);
        String executionId1 = bus.send(command);
        CommandExecution<?> execution1 = bus.getCommandExecution(executionId1);
        Thread.sleep(2000);
        execution1 = bus.getCommandExecution(executionId1);
        bus.register(MyCommand.class, commandHandler);

        Thread.sleep(1000);
        execution1 = bus.getCommandExecution(executionId1);
        bus.stop();
        Assert.assertEquals(CommandExecution.Status.Returned, execution1.getStatus());
        Assert.assertTrue(command.getReturnType().cast(execution1.getResult()).equals(1));
    }

    public class MyCommand implements Command<Integer>
    {
        int number;

        public MyCommand(int number)
        {
            this.number = number;
        }

        public int getNumber()
        {
            return number;
        }

        public Integer execute()
        {
            return number;
        }

        @Override
        public Class<Integer> getReturnType()
        {
            return Integer.class;
        }
    }

    public class MyCommand2 extends MyCommand
    {

        public MyCommand2(int number)
        {
            super(number);
        }
    }

    public class MyCommandHandler implements CommandHandler<MyCommand, Integer>
    {
        @Override
        public Integer handle(MyCommand command)
        {
            return command.getNumber();
        }

        @Override
        public Class<Integer> getReturnType()
        {
            return Integer.class;
        }

        @Override
        public Class<MyCommand> getCommandType()
        {
            return MyCommand.class;
        }
    }
}
