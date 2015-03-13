package com.amazon.extension.testrail.context;


import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.context.impl.TestrailAPIContextImpl;
import com.amazon.extension.testrail.context.impl.TestrailContextImpl;
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.restapi.APIClient;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;

public class ContextTest
{
    AppSystem testrailSystem;
    TestrailContext context;
    
    String url = "https://rcx-testrail.amazon.com/api.php?/api/v2";
    String username = "supernavy_trash@sina.com";
    String password = "Test123";

    private APIClient makeAPIClient()
    {
        APIClient client = new APIClient(url);
        client.setPassword(password);
        client.setUser(username);
        return client;
    }
    
    @BeforeTest
    public void init() throws Exception 
    {       
        testrailSystem = new SimpleTestrailSystem("Testrail System", Layer.Extension);
        new TestrailSystemAssembler(url, username, password).assemble(testrailSystem);
        TestrailAPIContext testrailAPIContext = new TestrailAPIContextImpl(makeAPIClient());
        context = new TestrailContextImpl(testrailSystem.getEventBus(), testrailAPIContext);
    }
    
    @AfterTest
    public void cleanUp() throws Exception {
    }
    
    @Test
    public void testAddTestPlan() throws Exception
    {
        Long projectId = 2L;
        JSONObject request = makeAddTestPlanRequest(projectId);
        JSONObject response = context.addTestPlan(projectId, request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.get(TestrailAPI.Key.Id));
    }
    
    @SuppressWarnings("unchecked")
    private JSONObject makeAddTestPlanRequest(Long projectId)
    {
        JSONObject addPlanRequest = new JSONObject();        
        addPlanRequest.put(TestrailAPI.Key.Name, "Fake Plan");
        
        return addPlanRequest;
    }
}
