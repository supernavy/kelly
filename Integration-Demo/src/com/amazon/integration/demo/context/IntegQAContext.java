package com.amazon.integration.demo.context;

import org.json.simple.JSONObject;
import com.amazon.infra.context.AppContextException;

public interface IntegQAContext
{
    public void addResultForCase(String testrailRunId, String testrailCaseId, JSONObject resultInfo) throws AppContextException;
    
}
