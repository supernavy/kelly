package com.amazon.integration.demo.context.impl;

import org.json.simple.JSONObject;
import com.amazon.infra.context.AppContextException;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.context.IntegQAContext;

public class IntegQAContextImpl implements IntegQAContext
{
    IntegContext integContext;

    @Override
    public void addResultForCase(String testrailRunId, String testrailCaseId, JSONObject resultInfo) throws AppContextException
    {
        // TODO Auto-generated method stub
        
    }

 

}
