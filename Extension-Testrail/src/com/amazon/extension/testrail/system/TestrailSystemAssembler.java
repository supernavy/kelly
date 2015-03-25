package com.amazon.extension.testrail.system;

import com.amazon.extension.testrail.api.TestrailException;
import com.amazon.extension.testrail.command.AddProjectCommand;
import com.amazon.extension.testrail.command.AddResultForCaseCommand;
import com.amazon.extension.testrail.command.AddTestPlanCommand;
import com.amazon.extension.testrail.command.AddTestSuiteCommand;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.command.DeleteTestPlanCommand;
import com.amazon.extension.testrail.command.DeleteTestSuiteCommand;
import com.amazon.extension.testrail.command.GetConfigurationsCommand;
import com.amazon.extension.testrail.command.GetPrioritiesCommand;
import com.amazon.extension.testrail.command.GetProjectCommand;
import com.amazon.extension.testrail.command.GetTestCasesCommand;
import com.amazon.extension.testrail.command.GetTestPlanCommand;
import com.amazon.extension.testrail.command.GetTestSuiteCommand;
import com.amazon.extension.testrail.command.GetTestsCommand;
import com.amazon.extension.testrail.command.UpdateTestSuiteCommand;
import com.amazon.extension.testrail.commandhandler.AddProjectCommandHandler;
import com.amazon.extension.testrail.commandhandler.AddResultForCaseCommandHandler;
import com.amazon.extension.testrail.commandhandler.AddTestPlanCommandHandler;
import com.amazon.extension.testrail.commandhandler.AddTestSuiteCommandHandler;
import com.amazon.extension.testrail.commandhandler.DeleteProjectCommandHandler;
import com.amazon.extension.testrail.commandhandler.DeleteTestPlanCommandHandler;
import com.amazon.extension.testrail.commandhandler.DeleteTestSuiteCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetConfigurationsCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetPrioritiesCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetProjectCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetTestCasesCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetTestPlanCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetTestSuiteCommandHandler;
import com.amazon.extension.testrail.commandhandler.GetTestsCommandHandler;
import com.amazon.extension.testrail.commandhandler.UpdateTestSuiteCommandHandler;
import com.amazon.extension.testrail.context.TestrailAPIContext;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.extension.testrail.context.impl.TestrailAPIContextImpl;
import com.amazon.extension.testrail.context.impl.TestrailContextImpl;
import com.amazon.infra.restapi.APIClient;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemAssembler;
import com.amazon.infra.system.AppSystemException;

public class TestrailSystemAssembler implements AppSystemAssembler
{
    APIClient client;
 
    public TestrailSystemAssembler(String url, String username, String password)
    {
        client = new APIClient(url);
        client.setUser(username);
        client.setPassword(password);
    }

    @Override
    public void assemble(AppSystem system) throws AppSystemException
    {        
        try {
            TestrailAPIContext testrailAPIContext = new TestrailAPIContextImpl(client);
            TestrailContext testrailContext = new TestrailContextImpl(system, testrailAPIContext);
            system.getCommandBus().register(GetPrioritiesCommand.class, new GetPrioritiesCommandHandler(testrailContext));

            system.getCommandBus().register(AddProjectCommand.class, new AddProjectCommandHandler(testrailContext));
            system.getCommandBus().register(GetProjectCommand.class, new GetProjectCommandHandler(testrailContext));
            system.getCommandBus().register(DeleteProjectCommand.class, new DeleteProjectCommandHandler(testrailContext));
            system.getCommandBus().register(GetConfigurationsCommand.class, new GetConfigurationsCommandHandler(testrailContext));
            
            system.getCommandBus().register(AddTestPlanCommand.class, new AddTestPlanCommandHandler(testrailContext));
            system.getCommandBus().register(GetTestPlanCommand.class, new GetTestPlanCommandHandler(testrailContext));
            system.getCommandBus().register(DeleteTestPlanCommand.class, new DeleteTestPlanCommandHandler(testrailContext));
            system.getCommandBus().register(GetTestsCommand.class, new GetTestsCommandHandler(testrailContext));
            system.getCommandBus().register(AddResultForCaseCommand.class, new AddResultForCaseCommandHandler(testrailContext));
            
            system.getCommandBus().register(AddTestSuiteCommand.class, new AddTestSuiteCommandHandler(testrailContext));
            system.getCommandBus().register(UpdateTestSuiteCommand.class, new UpdateTestSuiteCommandHandler(testrailContext));
            system.getCommandBus().register(GetTestSuiteCommand.class, new GetTestSuiteCommandHandler(testrailContext));
            system.getCommandBus().register(DeleteTestSuiteCommand.class, new DeleteTestSuiteCommandHandler(testrailContext));
            system.getCommandBus().register(GetTestCasesCommand.class, new GetTestCasesCommandHandler(testrailContext));
            
        } catch (TestrailException e) {
            throw new AppSystemException(e);
        }
    }

}
