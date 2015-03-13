package com.amazon.extension.testrail.context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.infra.context.AppContextException;

public interface TestrailContext
{   
    public JSONArray getPriorities() throws AppContextException;
    
    public JSONObject addProject(JSONObject data) throws AppContextException;
    public JSONObject getProject(Long id) throws AppContextException;
    public JSONObject deleteProject(Long id) throws AppContextException;
    public JSONArray getConfigurations(Long projectId) throws AppContextException;
    
    public JSONObject addSection(JSONObject data) throws AppContextException;
    public JSONObject getSection(Long id) throws AppContextException;
    
    public JSONObject addTestSuite(Long projectId, JSONObject data) throws AppContextException;
    public JSONObject getTestSuite(Long id) throws AppContextException;
    public JSONObject deleteTestSuite(Long id) throws AppContextException;
    
    public JSONObject addTestCase(JSONObject data) throws AppContextException;
    public JSONObject getTestCase(Long id) throws AppContextException;
    public JSONArray getTestCases(Long projectId, Long suiteId) throws AppContextException;
    
    public JSONObject addTestPlan(Long projectId, JSONObject data) throws AppContextException;
    public JSONObject getTestPlan(Long id) throws AppContextException;
    public JSONObject deleteTestPlan(Long id) throws AppContextException;
    
    public JSONArray getTests(Long runId) throws AppContextException;
    
    public JSONObject addResultForCase(Long runId, Long caseId, JSONObject data) throws AppContextException;
    public JSONArray addResultsForCases(Long runId, JSONObject data) throws AppContextException;
}
