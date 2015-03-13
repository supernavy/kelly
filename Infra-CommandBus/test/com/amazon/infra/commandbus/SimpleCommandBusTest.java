package com.amazon.infra.commandbus;

import java.util.List;
import org.testng.annotations.Test;
import com.amazon.infra.commandbus.CommandExecution;
import com.amazon.infra.commandbus.simple.SimpleCommandBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.impl.RepositoryMemoryImpl;

public class SimpleCommandBusTest {
    Repository<CommandExecution<?>> r1 = new RepositoryMemoryImpl<CommandExecution<?>>();
    Repository<List<String>> r2 = new RepositoryMemoryImpl<List<String>>();
    
	@Test
	public void testCommand() throws Exception {
		new CommandBusTest().testCommand(new SimpleCommandBus(r1, r2));
	}
	
	@Test
    public void testCommandTimeout() throws Exception {
        new CommandBusTest().testCommandTimeout(new SimpleCommandBus(r1, r2));
    }
	
	@Test
	public void testCommandHandler() throws Exception {
	    
	    new CommandBusTest().testCommandHandler(new SimpleCommandBus(r1,r2));
	}
	
	@Test
	public void testRegisterCommandHandlerAfterCommand() throws Exception {
	    new CommandBusTest().testRegisterCommandHandlerAfterCommand(new SimpleCommandBus(r1,r2));
	}
}
