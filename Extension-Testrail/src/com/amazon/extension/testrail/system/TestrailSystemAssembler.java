package com.amazon.extension.testrail.system;

import com.amazon.extension.testrail.api.TestrailException;
import com.amazon.extension.testrail.command.*;
import com.amazon.extension.testrail.commandhandler.*;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.extension.testrail.context.impl.TestrailServiceContextImpl;
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
            TestrailServiceContext testrailContext = new TestrailServiceContextImpl(system.getEventBus(), client);
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
            system.getCommandBus().register(GetTestSuiteCommand.class, new GetTestSuiteCommandHandler(testrailContext));
            system.getCommandBus().register(DeleteTestSuiteCommand.class, new DeleteTestSuiteCommandHandler(testrailContext));
            system.getCommandBus().register(GetTestCasesCommand.class, new GetTestCasesCommandHandler(testrailContext));
            
        } catch (TestrailException e) {
            throw new AppSystemException(e);
        }
    }

}
