package com.amazon.integration.demo.context;

import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;
import com.amazon.integration.demo.domain.entity.IntegQAProject;
import com.amazon.integration.demo.domain.entity.IntegQATestCase;
import com.amazon.integration.demo.domain.entity.IntegQATestSuite;

public interface IntegContext
{
    public Entity<IntegQAPlanRun> createIntegQAPlanRun(String qaPlanRunId, Long testrailProjectId) throws AppContextException;
    public Entity<IntegQAPlanRun> updateIntegQAPlanRun(String qaPlanRunId, IntegQAPlanRun integQAPlanRun) throws AppContextException;
    public Entity<IntegQAPlanRun> loadIntegQAPlanRun(String qaPlanRunId) throws AppContextException;
    
    public Entity<IntegQAProject> createIntegQAProject(String qaProjectId, Long testrailProjectId) throws AppContextException;
    public Entity<IntegQAProject> loadIntegQAProject(String qaProjectId) throws AppContextException;
    public Entity<IntegQAProject> addPlanToIntegQAProject(String qaProjectId, String planName, Long testrailSuiteId) throws AppContextException;
    public Entity<IntegQAProject> deletePlanFromIntegQAProject(String qaProjectId, String planName) throws AppContextException;
    public Entity<IntegQAProject> updateIntegQAProject(String qaProjectId, IntegQAProject integQAProject) throws AppContextException;
    
    public Entity<IntegQATestSuite> createIntegQATestSuite(String qaTestSuiteId, Long testrailProjectId, Long testrailSuiteId) throws AppContextException;
    public Entity<IntegQATestSuite> loadIntegQATestSuite(String qaTestSuiteId) throws AppContextException;
    
    public Entity<IntegQATestCase> createIntegQATestCase(String qaTestCaseId, Long testrailProjectId) throws AppContextException;
    public Entity<IntegQATestCase> loadIntegQATestCase(String qaTestCaseId) throws AppContextException;
}
