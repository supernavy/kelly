package com.amazon.extension.testrail.commandhandler;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.command.AddTestPlanCommand;
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;

public class CommandHandlerTest
{
    AppSystem testrailSystem;
    
    String url = "https://rcx-testrail.amazon.com/api.php?/api/v2";
    String username = "supernavy_trash@sina.com";
    String password = "Test123";
    
    @BeforeTest
    public void init() throws Exception 
    {
        testrailSystem = new SimpleTestrailSystem("demo testrail system", Layer.Extension);
        new TestrailSystemAssembler(url, username, password).assemble(testrailSystem);       
        
        testrailSystem.start();
    }
    
    @AfterTest
    public void cleanUp() throws Exception {
        testrailSystem.shutdown();
    }
    
    @Test
    public void testAddTestPlan() throws Exception
    {
        Long projectId = 2L;
        JSONObject requestData = makeAddTestPlanRequest(projectId);
        AddTestPlanCommand createTestPlanCommand = new AddTestPlanCommand(projectId, requestData);
        JSONObject responseData = testrailSystem.getCommandBus().submit(createTestPlanCommand).getResult();
        Assert.assertNotNull(responseData);
        Assert.assertNotNull(responseData.get(TestrailAPI.Key.Id));
    }
    
    @SuppressWarnings("unchecked")
    private JSONObject makeAddTestPlanRequest(Long projectId)
    {
        JSONObject addPlanRequest = new JSONObject();        
        addPlanRequest.put(TestrailAPI.Key.Name, "Fake Plan");
        
        return addPlanRequest;
    }
}
