package com.amazon.integration.demo.context;

import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.core.qa.domain.vo.planrun.TestCaseResult;
import com.amazon.infra.context.AppContextException;

public interface IntegTestrailContext
{
    public JSONObject createTestrailPlan(String qaPlanRunId) throws AppContextException;
    public JSONObject updateTestrailPlan(String qaPlanRunId) throws AppContextException;
    public JSONObject loadTestrailPlan(String qaPlanRunId) throws AppContextException;
    
    public JSONObject createTestrailProject(String qaProjectId) throws AppContextException;
    public JSONObject loadTestrailProject(String qaProjectId) throws AppContextException;
    public JSONObject updateTestrailProject(String qaProjectId) throws AppContextException;
    public JSONArray loadTestrailConfigurations(String qaProjectId) throws AppContextException;
    
    public JSONObject createTestrailTestSuite(String qaProjectId, String planName) throws AppContextException;
    public JSONObject loadTestrailTestSuite(String qaProjectId, String planName) throws AppContextException;
    public JSONObject deleteTestrailTestSuite(String qaProjectId, String planName) throws AppContextException;
    
    public JSONObject createTestrailTestCase(String qaTestCaseId) throws AppContextException;
    public JSONObject loadTestrailTestCase(String qaTestCaseId) throws AppContextException;
    public JSONArray loadTestrailTestCases(String qaTestSuiteId) throws AppContextException;
    
    public JSONObject addResultForCase(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, Long caseId, TestCaseResult resultInfo) throws AppContextException;
    public JSONArray addResultForAutomation(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, String automationId, TestCaseResult resultInfo) throws AppContextException;
    public JSONArray addResultsForRun(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, Map<Long, TestCaseResult> resultInfos) throws AppContextException;
}
