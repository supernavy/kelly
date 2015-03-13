package com.amazon.core.qa.context;

import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.infra.context.AppContext;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public interface PlanRunContext extends AppContext
{
    public Entity<PlanRun> createTestPlanRun(String projectId, String planName, String releaseBuildId) throws AppContextException;
    
    public Entity<PlanRun> load(String planRunId) throws AppContextException;
    
//    public void startRun(PlanRun run) throws QAContextException;
//    public void stopRun(PlanRun run) throws QAContextException;
//    
//    public void startEntryRun(PlanRun run, PlanEntryRun entryRun) throws QAContextException;
//    public void stopEntryRun(PlanRun run) throws QAContextException;
//    
//    public void startTestCaseRun(PlanRun run, PlanEntryRun entryRun, TestCase testCase) throws QAContextException;
//    public void updateTestCaseRunResult(PlanRun run, PlanEntryRun entryRun, TestCase testCase, TestCaseRunResult testCaseResult) throws QAContextException;
//    public void endTestCaseRunResult(PlanRun run, PlanEntryRun entryRun, TestCase testCase, TestCaseRunResult testCaseResult) throws QAContextException;
}
