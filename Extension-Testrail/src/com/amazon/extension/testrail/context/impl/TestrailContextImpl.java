package com.amazon.extension.testrail.context.impl;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.context.TestrailAPIContext;
import com.amazon.extension.testrail.context.TestrailContext;
import com.amazon.extension.testrail.event.ProjectAddedEvent;
import com.amazon.extension.testrail.event.ProjectDeletedEvent;
import com.amazon.extension.testrail.event.TestPlanAddedEvent;
import com.amazon.extension.testrail.event.TestPlanDeletedEvent;
import com.amazon.extension.testrail.event.TestSuiteAddedEvent;
import com.amazon.extension.testrail.event.TestSuiteDeletedEvent;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.implementation.NotImplementedException;

public class TestrailContextImpl extends AbsAppContextImpl implements TestrailContext
{
    TestrailAPIContext testrailAPIContext;
    
    public TestrailContextImpl(EventBus eventBus, TestrailAPIContext testrailAPIContext)
    {
        super(eventBus);
        this.testrailAPIContext = testrailAPIContext;
    }
    
    @Override
    public JSONArray getPriorities() throws AppContextException
    {
        return testrailAPIContext.sendGet(TestrailAPI.Method.GET_PRIORITIES, new Object[]{}, null);
    }

    @Override
    public JSONObject addProject(JSONObject data) throws AppContextException
    {
        return testrailAPIContext.sendPost(TestrailAPI.Method.ADD_PROJECT, new Object[]{}, data);
    }

    @Override
    public JSONObject getProject(Long id) throws AppContextException
    {
        JSONObject p = testrailAPIContext.sendGet(TestrailAPI.Method.GET_PROJECT, new Object[]{id}, null);
        publishEvent(new ProjectAddedEvent((Long) p.get(TestrailAPI.Key.Id)));
        return p;
    }

    @Override
    public JSONObject deleteProject(Long id) throws AppContextException
    {
        JSONObject p = testrailAPIContext.sendPost(TestrailAPI.Method.DELETE_PROJECT, new Object[]{id}, null);
        publishEvent(new ProjectDeletedEvent(id));
        return p;
    }

    @Override
    public JSONArray getConfigurations(Long projectId) throws AppContextException
    {
        return testrailAPIContext.sendGet(TestrailAPI.Method.GET_CONFIGURATIONS, new Object[]{projectId}, null);
    }

    @Override
    public JSONObject addSection(JSONObject data) throws AppContextException
    {
        throw new NotImplementedException();
    }

    @Override
    public JSONObject getSection(Long id) throws AppContextException
    {
        throw new NotImplementedException();
    }

    @Override
    public JSONObject addTestSuite(Long projectId, JSONObject data) throws AppContextException
    {
        JSONObject s = testrailAPIContext.sendPost(TestrailAPI.Method.ADD_SUITE, new Object[]{projectId}, data);
        publishEvent(new TestSuiteAddedEvent(projectId, (Long) s.get(TestrailAPI.Key.Id)));
        return s;
    }

    @Override
    public JSONObject getTestSuite(Long id) throws AppContextException
    {
        return testrailAPIContext.sendGet(TestrailAPI.Method.GET_SUITE, new Object[]{id}, null);
    }

    @Override
    public JSONObject deleteTestSuite(Long id) throws AppContextException
    {
        JSONObject s = testrailAPIContext.sendPost(TestrailAPI.Method.DELETE_SUITE, new Object[]{id}, null);
        publishEvent(new TestSuiteDeletedEvent(id));
        return s;
    }

    @Override
    public JSONObject addTestCase(JSONObject data) throws AppContextException
    {
        throw new NotImplementedException();
    }

    @Override
    public JSONObject getTestCase(Long id) throws AppContextException
    {
        throw new NotImplementedException();
    }

    @Override
    public JSONArray getTestCases(Long projectId, Long suiteId) throws AppContextException
    {
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put(TestrailAPI.Key.Suite_Id, suiteId);
        return testrailAPIContext.sendGet(TestrailAPI.Method.GET_CASES, new Object[]{projectId}, filter);
    }

    @Override
    public JSONObject addTestPlan(Long projectId, JSONObject data) throws AppContextException
    {
        JSONObject tp = testrailAPIContext.sendPost(TestrailAPI.Method.ADD_PLAN, new Object[]{projectId}, data);
        publishEvent(new TestPlanAddedEvent(projectId, (Long) tp.get(TestrailAPI.Key.Id)));
        return tp;
    }

    @Override
    public JSONObject getTestPlan(Long id) throws AppContextException
    {
        return testrailAPIContext.sendGet(TestrailAPI.Method.GET_PLAN, new Object[]{id}, null);
    }

    @Override
    public JSONObject deleteTestPlan(Long id) throws AppContextException
    {
        JSONObject tp = testrailAPIContext.sendPost(TestrailAPI.Method.DELETE_PLAN, new Object[]{id}, null);
        publishEvent(new TestPlanDeletedEvent(id));
        return tp;
    }

    @Override
    public JSONArray getTests(Long runId) throws AppContextException
    {
        return testrailAPIContext.sendGet(TestrailAPI.Method.GET_TESTS, new Object[]{runId}, null);
    }

    @Override
    public JSONObject addResultForCase(Long runId, Long caseId, JSONObject data) throws AppContextException
    {
        return testrailAPIContext.sendPost(TestrailAPI.Method.ADD_RESULT_FOR_CASE, new Object[]{runId, caseId}, data);
    }

    @Override
    public JSONArray addResultsForCases(Long runId, JSONObject data) throws AppContextException
    {
        throw new NotImplementedException();
    }

}
