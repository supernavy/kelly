package com.amazon.integration.demo.context.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.core.qa.domain.entity.Project;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.planrun.TestCaseResult;
import com.amazon.core.qa.domain.vo.project.PlanEntry;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.extension.testrail.api.ConfigurationSelector;
import com.amazon.extension.testrail.api.TestCaseSelector;
import com.amazon.extension.testrail.api.TestrailAPI;
import com.amazon.extension.testrail.api.TestrailProjectBuilder;
import com.amazon.extension.testrail.api.TestrailProjectContext;
import com.amazon.extension.testrail.api.TestrailRootContext;
import com.amazon.extension.testrail.api.TestrailTestPlanBuilder;
import com.amazon.extension.testrail.api.TestrailTestPlanBuilder.TestPlanEntryBuilder;
import com.amazon.extension.testrail.command.AddProjectCommand;
import com.amazon.extension.testrail.command.AddResultForCaseCommand;
import com.amazon.extension.testrail.command.AddResultsForCasesCommand;
import com.amazon.extension.testrail.command.AddTestPlanCommand;
import com.amazon.extension.testrail.command.AddTestSuiteCommand;
import com.amazon.extension.testrail.command.GetConfigurationsCommand;
import com.amazon.extension.testrail.command.GetPrioritiesCommand;
import com.amazon.extension.testrail.command.GetProjectCommand;
import com.amazon.extension.testrail.command.GetTestCaseCommand;
import com.amazon.extension.testrail.command.GetTestCasesCommand;
import com.amazon.extension.testrail.command.GetTestPlanCommand;
import com.amazon.extension.testrail.command.GetTestSuiteCommand;
import com.amazon.infra.commandbus.CommandBus;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.implementation.NotImplementedException;
import com.amazon.integration.demo.context.IntegContext;
import com.amazon.integration.demo.context.IntegTestrailContext;
import com.amazon.integration.demo.domain.entity.IntegQAPlanRun;
import com.amazon.integration.demo.domain.entity.IntegQAProject;
import com.amazon.integration.demo.domain.entity.IntegQATestCase;
import com.amazon.integration.demo.domain.entity.IntegQATestSuite;

public class IntegTestrailContextImpl extends AbsAppContextImpl implements IntegTestrailContext
{
    Logger logger = Logger.getLogger(this.getClass().getName());
    IntegContext integContext;
    CommandBus qaSystemCommandBus;
    CommandBus testrailSystemCommandBus;
    
    public IntegTestrailContextImpl(EventBus eventBus, IntegContext integContext, CommandBus qaSystemCommandBus, CommandBus testrailSystemCommandBus)
    {
        super(eventBus);
        this.integContext = integContext;
        this.qaSystemCommandBus = qaSystemCommandBus;
        this.testrailSystemCommandBus = testrailSystemCommandBus;
    }
    
    @Override
    public JSONObject createTestrailPlan(String qaPlanRunId) throws AppContextException
    {
        try {
            Entity<IntegQAPlanRun> integQAPlanRunEntity = integContext.loadIntegQAPlanRun(qaPlanRunId);
            if(integQAPlanRunEntity==null || integQAPlanRunEntity.getData().getTestrailTestPlanId()==null)
            {
                Entity<PlanRun> qaPlanRunInfo = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetPlanRunCommand(qaPlanRunId)).getResult();
                Entity<IntegQAProject> integQAProjectEntity = integContext.loadIntegQAProject(qaPlanRunInfo.getData().getProjectInfo().getId());
                
                TestrailRootContext testrailInstance = new TestrailRootContext(loadTestrailPriorities());
                JSONObject testrailProject = loadTestrailProject(qaPlanRunInfo.getData().getProjectInfo().getId());
                JSONArray testrailProjectConfigurations = loadTestrailConfigurations(qaPlanRunInfo.getData().getProjectInfo().getId());
                TestrailProjectContext projectInstance = testrailInstance.newProjectInstance(testrailProject, testrailProjectConfigurations);
                
                TestrailTestPlanBuilder testPlanBuilder = projectInstance.newTestPlanBuilder();
                testPlanBuilder.setName(getPlanName(qaPlanRunInfo));
                testPlanBuilder.setDescription(getPlanDescription(qaPlanRunInfo));

                Map<Long, JSONObject> testrailTestSuiteCache = new HashMap<Long, JSONObject>();
                Map<Long, JSONArray> testrailTestCasesCache = new HashMap<Long, JSONArray>();
                for (PlanEntry planEntry : qaPlanRunInfo.getData().getPlan().getPlanEntries().values()) {
                    String entryName = planEntry.getName();
                    Long testrailTestSuiteId = integQAProjectEntity.getData().getPlanSuiteIds().get(qaPlanRunInfo.getData().getPlan().getName());
                    TestPlanEntryBuilder testPlanEntryBuilder = testPlanBuilder.newTestPlanEntryBuilder(entryName, loadCachedTestrailTestSuite(testrailTestSuiteId, testrailTestSuiteCache), loadCachedTestrailTestCases(integQAProjectEntity.getData().getTestrailProjectId(), testrailTestSuiteId, testrailTestCasesCache));
                    testPlanEntryBuilder.filterCaseIds(new TestrailCaseSelector(planEntry, testrailInstance.getTestrailPriorities()));
                    testPlanEntryBuilder.filterConfigIds(new TestrailConfigurationsSelector(planEntry));
                    JSONObject entry = testPlanEntryBuilder.build();
                    testPlanBuilder.addEntry(entry);
                }

                JSONObject testPlanRequest = testPlanBuilder.build();
                JSONObject testPlan = testrailSystemCommandBus.submit(new AddTestPlanCommand((Long) projectInstance.getTestrailProject().get(TestrailAPI.Key.Id), testPlanRequest)).getResult();
                Long testrailTestPlantId = (Long) testPlan.get(TestrailAPI.Key.Id);
                if(integQAPlanRunEntity==null)
                {
                    logger.fine(String.format("there is no IntegPlanRunEntity for QAPlanRun[%s], creating", qaPlanRunInfo));
                    integQAPlanRunEntity = integContext.createIntegQAPlanRun(qaPlanRunInfo.getId(), testrailTestPlantId);
                } else {
                    logger.fine(String.format("there is no TestrailTestPlan created for integQAPlanRunEntity[%s], updating", integQAPlanRunEntity));
                    IntegQAPlanRun newIntegQAPlanRun = new IntegQAPlanRun(qaPlanRunId, testrailTestPlantId);
                    integQAPlanRunEntity = integContext.updateIntegQAPlanRun(qaPlanRunId, newIntegQAPlanRun);
                }
                return testPlan;
            }
            
            throw new AppContextException(String.format("there is already TestrailTestPlan[%s] mapping for qaPlanRunId[%s]",integQAPlanRunEntity.getData().getTestrailTestPlanId(), qaPlanRunId));
  
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private JSONObject loadCachedTestrailTestSuite(Long testrailTestSuiteId, Map<Long, JSONObject> testrailTestSuiteCaches) throws AppContextException
    {
        if(!testrailTestSuiteCaches.containsKey(testrailTestSuiteId))
        {
            try {
                JSONObject suite =  testrailSystemCommandBus.submit(new GetTestSuiteCommand(testrailTestSuiteId)).getResult();
                testrailTestSuiteCaches.put(testrailTestSuiteId, suite);
            } catch (Exception e) {
                throw new AppContextException(e);
            }
        }
        return testrailTestSuiteCaches.get(testrailTestSuiteId);
    }
    
    private JSONArray loadCachedTestrailTestCases(Long testrailProjectId, Long testrailTestSuiteId, Map<Long, JSONArray> testrailTestCasesCaches) throws AppContextException
    {
        if(!testrailTestCasesCaches.containsKey(testrailTestSuiteId))
        {
            try {
                JSONArray cases =  testrailSystemCommandBus.submit(new GetTestCasesCommand(testrailProjectId, testrailTestSuiteId)).getResult();
                testrailTestCasesCaches.put(testrailTestSuiteId, cases);
            } catch (Exception e) {
                throw new AppContextException(e);
            }
        }
        return testrailTestCasesCaches.get(testrailTestSuiteId);
    }
    
    private String getPlanName(Entity<PlanRun> qaPlanRunEntity)
    {
        Entity<Product> productInfo = qaPlanRunEntity.getData().getProjectInfo().getData().getProductInfo();
        Entity<Build> buildInfo = qaPlanRunEntity.getData().getBuildInfo();
        String name = String.format("%s for %s %s %s", qaPlanRunEntity.getData().getPlan().getName(), productInfo.getData().getName(), buildInfo.getData().getBuildName(), buildInfo.getData().getPatchName());
        return name;
    }

    private String getPlanDescription(Entity<PlanRun> qaPlanRunEntity)
    {
        return getPlanName(qaPlanRunEntity);
    }
    
    private JSONArray loadTestrailPriorities() throws AppContextException
    {
        try {
            return testrailSystemCommandBus.submit(new GetPrioritiesCommand()).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        } 
    }
    
    @Override
    public JSONObject loadTestrailPlan(String qaPlanRunId) throws AppContextException
    {    
        try {
            Entity<IntegQAPlanRun> integQAPlanRunInfo = integContext.loadIntegQAPlanRun(qaPlanRunId);
            Long testrailTestPlanId = integQAPlanRunInfo.getData().getTestrailTestPlanId();
            return testrailSystemCommandBus.submit(new GetTestPlanCommand(testrailTestPlanId)).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        } 
    }
    
    private JSONObject loadTestrailPlan(Entity<PlanRun> qaPlanRunInfo) throws AppContextException
    {    
        try {
            return loadTestrailPlan(qaPlanRunInfo.getId());
        } catch (Exception e) {
            throw new AppContextException(e);
        } 
    }

    @Override
    public JSONObject createTestrailProject(String qaProjectId) throws AppContextException
    {       
        try {
            Entity<IntegQAProject> qaIntegProjectEntity = integContext.loadIntegQAProject(qaProjectId);
            if(qaIntegProjectEntity==null || qaIntegProjectEntity.getData().getTestrailProjectId()==null)
            {
                Entity<Project> qaProjectInfo = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetProjectCommand(qaProjectId)).getResult();
                TestrailRootContext testrailInstance = new TestrailRootContext(loadTestrailPriorities());
                TestrailProjectBuilder testrailProjectBuilder = testrailInstance.newProjectBuilder();
                JSONObject data = testrailProjectBuilder.setName(mapTestrailProjectName(qaProjectInfo)).setDescription(mapTestrailProjectDescription(qaProjectInfo)).build();
          
                JSONObject testrailProject = testrailSystemCommandBus.submit(new AddProjectCommand(data)).getResult();
                Long testrailProjectId = (Long) testrailProject.get(TestrailAPI.Key.Id);
                if(qaIntegProjectEntity==null)
                {
                    logger.fine(String.format("there is no IntegProjectEntity for qaProject[%s], creating", qaProjectInfo));
                    qaIntegProjectEntity = integContext.createIntegQAProject(qaProjectId, testrailProjectId);
                } else {
                    logger.fine(String.format("there is no TestrailProject created for IntegProjectEntity[%s], updating", qaIntegProjectEntity));
                    IntegQAProject newIntegQaProject = new IntegQAProject(qaProjectId, testrailProjectId, null);
                    qaIntegProjectEntity = integContext.updateIntegQAProject(qaProjectId, newIntegQaProject);
                }
                return testrailProject;
            }
            
            throw new AppContextException(String.format("there is already TestrailProject[%s] mapping for qaProjectId[%s]",qaIntegProjectEntity.getData().getTestrailProjectId(), qaProjectId));
        } catch (Exception e) {
            throw new AppContextException(e);
        } 
    }
    
    private String mapTestrailProjectName(Entity<Project> qaProjectInfo)
    {
        return qaProjectInfo.getData().getName();
    }
    
    private String mapTestrailProjectDescription(Entity<Project> qaProjectInfo)
    {
        return "no description";
    }

    @Override
    public JSONObject loadTestrailProject(String qaProjectId) throws AppContextException
    {    
        try {
            Entity<IntegQAProject> integQAProjectInfo = integContext.loadIntegQAProject(qaProjectId);
            Long testrailProjectId = integQAProjectInfo.getData().getTestrailProjectId();
            JSONObject testrailProject;
            testrailProject = testrailSystemCommandBus.submit(new GetProjectCommand(testrailProjectId)).getResult();
            return testrailProject;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public JSONArray loadTestrailConfigurations(String qaProjectId) throws AppContextException
    {     
        try {
            Entity<IntegQAProject> integQAProjectInfo = integContext.loadIntegQAProject(qaProjectId);
            Long testrailProjectId = integQAProjectInfo.getData().getTestrailProjectId();
            return testrailSystemCommandBus.submit(new GetConfigurationsCommand(testrailProjectId)).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject createTestrailTestSuite(String qaProjectId, String planName) throws AppContextException
    {
        try {
            Entity<IntegQAProject> integQAProjectInfo = integContext.loadIntegQAProject(qaProjectId);
            if(integQAProjectInfo == null || integQAProjectInfo.getData().getTestrailProjectId() == null)
            {
                throw new AppContextException(String.format("integQAProjectInfo[%s] or its testrail project id is null.", integQAProjectInfo));
            }
            Long testrailProjectId = integQAProjectInfo.getData().getTestrailProjectId();
            
            JSONObject postData = new JSONObject();
            postData.put(TestrailAPI.Key.Name, planName);
            postData.put(TestrailAPI.Key.Description, String.format("%s-AutoCreated", planName));
            JSONObject testrailTestSuite = testrailSystemCommandBus.submit(new AddTestSuiteCommand(testrailProjectId, postData)).getResult();
            
            Map<String, Long> integQAProjectPlans = integQAProjectInfo.getData().getPlanSuiteIds();
            integQAProjectPlans.put(planName, (Long) testrailTestSuite.get(TestrailAPI.Key.Id));
            integQAProjectInfo = integContext.addPlanToIntegQAProject(qaProjectId, planName, (Long) testrailTestSuite.get(TestrailAPI.Key.Id));
            return testrailTestSuite;
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public JSONObject loadTestrailTestSuite(String qaProjectId, String planName) throws AppContextException
    {      
        try {
            Entity<IntegQAProject> integQAProjectInfo = integContext.loadIntegQAProject(qaProjectId);
            Long testrailTestSuitetId = integQAProjectInfo.getData().getPlanSuiteIds().get(planName);
            return testrailSystemCommandBus.submit(new GetTestSuiteCommand(testrailTestSuitetId)).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public JSONObject createTestrailTestCase(String qaTestCaseId) throws AppContextException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JSONObject loadTestrailTestCase(String qaTestCaseId) throws AppContextException
    {
        Entity<IntegQATestCase> integQATestCaseInfo = integContext.loadIntegQATestCase(qaTestCaseId);
        
        try {
            return testrailSystemCommandBus.submit(new GetTestCaseCommand(integQATestCaseInfo.getData().getTestrailTestCaseId())).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    @Override
    public JSONArray loadTestrailTestCases(String qaTestSuiteId) throws AppContextException
    {
        Entity<IntegQATestSuite> integQATestSuiteInfo = integContext.loadIntegQATestSuite(qaTestSuiteId);
        try {
            return testrailSystemCommandBus.submit(new GetTestCasesCommand(integQATestSuiteInfo.getData().getTestrailProjectId(), integQATestSuiteInfo.getData().getTestrailTestSuiteId())).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public JSONObject addResultForCase(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, Long caseId, TestCaseResult resultInfo) throws AppContextException
    {
        try {
            logger.fine(String.format("qaPlanRunId[%s],qaProjectId[%s]",qaPlanRunId,qaProjectId));
            Entity<PlanRun> qaPlanRunInfo = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetPlanRunCommand(qaPlanRunId)).getResult();
            
            Entity<Project> qaProjectInfo = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetProjectCommand(qaProjectId)).getResult();    

            return addResultForCase(qaPlanRunInfo, qaProjectInfo, qaProjectPlanName, qaProjectPlanEntryName, caseId, resultInfo);
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private JSONObject addResultForCase(Entity<PlanRun> qaPlanRunInfo, Entity<Project> qaProjectInfo, String qaProjectPlanName, String qaProjectPlanEntryName, Long caseId, TestCaseResult resultInfo) throws AppContextException
    {
        try {
            JSONObject testrailTestPlan = loadTestrailPlan(qaPlanRunInfo);
            if(testrailTestPlan == null)
            {
                throw new RuntimeException(String.format("not found related testrail plan. qaPlanRunInfo[%s]", qaPlanRunInfo));
            }
            
            PlanEntry planEntryInfo = null;
            System.out.println(qaProjectInfo.getData().getName()+"   "+qaProjectInfo.getData().getProductInfo()+"   "+qaProjectInfo.getData());
            for(PlanEntry planEntry : qaProjectInfo.getData().getPlans().get(qaProjectPlanName).getPlanEntries().values())
            {
                if(planEntry.getName().equals(qaProjectPlanEntryName))
                {
                    planEntryInfo = planEntry;
                    break;
                }
            }
            if(planEntryInfo == null)
            {
                throw new RuntimeException(String.format("not found related plan entry. Entity<Project>[%s],qaProjectPlanName[%s],qaProjectPlanEntryName[%s]", qaProjectInfo, qaProjectPlanName, qaProjectPlanEntryName));
            }
            
            JSONObject foundRun = findEntryRun(qaPlanRunInfo, planEntryInfo, testrailTestPlan);
            if (foundRun == null) {
                throw new RuntimeException(String.format("not found run in related testrail plan. testrailTestPlan[%s], Entity<PlanRun>[%s], RunConfig[%s]", testrailTestPlan, qaPlanRunInfo, planEntryInfo));
            }

            JSONObject postData = new JSONObject();
            postData.put(TestrailAPI.Key.Status_Id, mapStatusId(resultInfo));
            postData.put(TestrailAPI.Key.Comment, mapComment(resultInfo));
            postData.put(TestrailAPI.Key.Elapsed, mapElapsed(resultInfo));
            postData.put(TestrailAPI.Key.Version, mapVersion(resultInfo));

            return testrailSystemCommandBus.submit(new AddResultForCaseCommand((Long) foundRun.get(TestrailAPI.Key.Id), caseId, postData)).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    private Long mapStatusId(TestCaseResult resultInfo)
    {
        TestCaseResult.Status s = resultInfo.getStatus();
        switch(s)
        {
            case Pass: return 1L;
            case Fail: return 5L;
            case NotRun: return 3L;
            default: return 5L;
        }
    }
    
    private String mapComment(TestCaseResult resultInfo)
    {
        return resultInfo.getMessage();
    }
    
    private String mapElapsed(TestCaseResult resultInfo)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(resultInfo.getStartTime());
        int s1 = cal.get(Calendar.SECOND);
        cal.setTime(resultInfo.getEndTime());
        int s2 = cal.get(Calendar.SECOND);
        
        return String.format("%ds", s2-s1);
    }
    
    private String mapVersion(TestCaseResult resultInfo)
    {
        return "1.0";
    }
    
    private JSONObject findEntryRun(Entity<PlanRun> qaPlanRunInfo, PlanEntry planEntryInfo, JSONObject testrailTestPlan)
    {
        JSONArray entries = (JSONArray) testrailTestPlan.get(TestrailAPI.Key.Entries);
        if (entries == null) {
            return null;
        }

        String entryName = planEntryInfo.getName();
 
        for (Object obj : entries) {
            JSONObject entry = (JSONObject) obj;
            if(entryName.equals(entry.get(TestrailAPI.Key.Name)))
            {
                JSONArray runs = (JSONArray) entry.get(TestrailAPI.Key.Runs);
                if (runs == null) {
                    return null;
                }

                return (JSONObject) runs.get(0);
            }
            
        }
        return null;
    }

    @Override
    public JSONArray addResultsForRun(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, Map<Long, TestCaseResult> resultInfos) throws AppContextException
    {
        try {
            Entity<PlanRun> qaPlanRunInfo = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetPlanRunCommand(qaPlanRunId)).getResult();
            
            Entity<Project> qaProjectInfo = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetProjectCommand(qaProjectId)).getResult();    

            return addResultsForRun(qaPlanRunInfo, qaProjectInfo, qaProjectPlanName, qaProjectPlanEntryName, resultInfos);
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private JSONArray addResultsForRun(Entity<PlanRun> qaPlanRunInfo, Entity<Project> qaProjectInfo, String qaProjectPlanName, String qaProjectPlanEntryName, Map<Long, TestCaseResult> resultInfos) throws AppContextException
    {
        JSONObject testrailTestPlan = loadTestrailPlan(qaPlanRunInfo);
        if(testrailTestPlan == null)
        {
            throw new RuntimeException(String.format("not found related testrail plan. Entity<PlanRun>[%s]", qaPlanRunInfo));
        }
        
        PlanEntry planEntryInfo = null;
        for(PlanEntry planEntry : qaProjectInfo.getData().getPlans().get(qaProjectPlanName).getPlanEntries().values())
        {
            if(planEntry.getName().equals(qaProjectPlanEntryName))
            {
                planEntryInfo = planEntry;
                break;
            }
        }
        if(planEntryInfo == null)
        {
            throw new RuntimeException(String.format("not found related plan entry. Entity<Project>[%s],qaProjectPlanName[%s],qaProjectPlanEntryName[%s]", qaProjectInfo, qaProjectPlanName, qaProjectPlanEntryName));
        }
        
        JSONObject foundRun = findEntryRun(qaPlanRunInfo, planEntryInfo, testrailTestPlan);
        if (foundRun == null) {
            throw new RuntimeException(String.format("not found run in related testrail plan. testrailTestPlan[%s], Entity<PlanRun>[%s], RunConfig[%s]", testrailTestPlan, qaPlanRunInfo, planEntryInfo));
        }
        JSONArray resultsData = new JSONArray();
        for(Long caseId: resultInfos.keySet())
        {
            TestCaseResult resultInfo = resultInfos.get(caseId);
            try {
                JSONObject resultData = new JSONObject();
                resultData.put(TestrailAPI.Key.Case_Id, caseId);
                resultData.put(TestrailAPI.Key.Status_Id, mapStatusId(resultInfo));
                resultData.put(TestrailAPI.Key.Comment, mapComment(resultInfo));
                resultData.put(TestrailAPI.Key.Elapsed, mapElapsed(resultInfo));
                resultData.put(TestrailAPI.Key.Version, mapVersion(resultInfo));
    
                resultsData.add(resultData);
            } catch (Exception e) {
                throw new AppContextException(e);
            }
        }
        
        JSONObject data = new JSONObject();
        data.put(TestrailAPI.Key.Results, resultsData);
        try {
            return testrailSystemCommandBus.submit(new AddResultsForCasesCommand((Long) foundRun.get(TestrailAPI.Key.Id), data)).getResult();
        } catch (Exception e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public JSONArray addResultForAutomation(String qaPlanRunId, String qaProjectId, String qaProjectPlanName, String qaProjectPlanEntryName, String automationId, TestCaseResult resultInfo) throws AppContextException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    private class TestrailCaseSelector implements TestCaseSelector
    {
        PlanEntry run;
        JSONArray testrailPriorities;

        List<JSONObject> selectTestrailPriorities;

        public TestrailCaseSelector(PlanEntry run, JSONArray testrailPriorities)
        {
            this.run = run;
            this.testrailPriorities = testrailPriorities;
            initSelectedTestrailPriorities();
        }

        @Override
        public boolean matches(JSONObject testCase)
        {
            if (matchPriority(testCase) && matchRunnableLocales(testCase) && matchRunnablePlatform(testCase)) {
                return true;
            }
            return false;
        }

        private void initSelectedTestrailPriorities()
        {
            selectTestrailPriorities = new ArrayList<JSONObject>();
            List<Priority> equalAndBelow = Priority.equalAndBlow(run.getPriority());
            for (Priority p : equalAndBelow) {
                JSONObject tp = findTestRailPriority(p);
                if (tp == null) {
                    throw new RuntimeException(String.format("Can't map %s[%s] to TestRail configuration", Priority.class.getName(), p));
                }
                selectTestrailPriorities.add(tp);
            }
        }

        private boolean matchRunnableLocales(JSONObject c)
        {
            JSONArray runnableLocales = (JSONArray) c.get(TestrailAPI.Key.Custom_Prefix + "runnablelocales");
            /**
             *  1,US
                2,CA
                3,CN
                4,JP
                5,DE
                6,UK
                7,FR
                8,IT
                9,ES
                10,IN
                11,BR
             */
            switch (run.getLocale()) {
                case US:
                    return runnableLocales.contains(Long.valueOf(1));
                case CA:
                    return runnableLocales.contains(Long.valueOf(2));
                case CN:
                    return runnableLocales.contains(Long.valueOf(3));
                case JP:
                    return runnableLocales.contains(Long.valueOf(4));
                case DE:
                    return runnableLocales.contains(Long.valueOf(5));
                case UK:
                    return runnableLocales.contains(Long.valueOf(6));
                case FR:
                    return runnableLocales.contains(Long.valueOf(7));
                case IT:
                    return runnableLocales.contains(Long.valueOf(8));
                case ES:
                    return runnableLocales.contains(Long.valueOf(9));
                case IN:
                    return runnableLocales.contains(Long.valueOf(10));
                case BR:
                    return runnableLocales.contains(Long.valueOf(11));
                case Any:
                    return true;
                default:
                    return false;
            }
        }

        private boolean matchPriority(JSONObject c)
        {
            for (JSONObject p : selectTestrailPriorities) {
                if (c.get(TestrailAPI.Key.Priority_Id).equals(p.get(TestrailAPI.Key.Id))) {
                    return true;
                }
            }
            return false;
        }

        private boolean matchRunnablePlatform(JSONObject c)
        {
            JSONArray runnablePlatforms = (JSONArray) c.get(TestrailAPI.Key.Custom_Prefix + "platform");
            switch (run.getView()) {
                case Desktop:
                    return runnablePlatforms.contains(Long.valueOf(1)) || runnablePlatforms.contains(Long.valueOf(2));
                case MobileWeb:
                    return runnablePlatforms.contains(Long.valueOf(1)) || runnablePlatforms.contains(Long.valueOf(3));
                case MobileApp:
                    return runnablePlatforms.contains(Long.valueOf(1)) || runnablePlatforms.contains(Long.valueOf(4));
                case TabletWeb:
                    return runnablePlatforms.contains(Long.valueOf(1)) || runnablePlatforms.contains(Long.valueOf(5));
                case TabletApp:
                    return runnablePlatforms.contains(Long.valueOf(1)) || runnablePlatforms.contains(Long.valueOf(6));
                default:
                    return false;
            }
        }

        private JSONObject findTestRailPriority(Priority p)
        {
            for (Object o : testrailPriorities) {
                JSONObject tp = (JSONObject) o;
                if (tp.get(TestrailAPI.Key.Name).equals(p.getTitle())) {
                    return tp;
                }
            }
            return null;
        }
    }

    private class TestrailConfigurationsSelector implements ConfigurationSelector
    {
        PlanEntry run;

        public TestrailConfigurationsSelector(PlanEntry run)
        {
            this.run = run;
        }

        private boolean matchLocale(JSONObject group, JSONObject config)
        {
            if (group.get(TestrailAPI.Key.Name).equals("Locale") && config.get(TestrailAPI.Key.Name).equals(run.getLocale().name())) {
                return true;
            }
            return false;
        }

        private boolean matchPlatformBrowser(JSONObject group, JSONObject config)
        {
            switch (run.getPlatform()) {
                case Desktop:
                    return group.get(TestrailAPI.Key.Name).equals("Desktop") && config.get(TestrailAPI.Key.Name).equals(run.getLocale().name());
                default:
                    return false;
            }
        }

        @Override
        public boolean select(JSONObject group, JSONObject config)
        {
            if (matchLocale(group, config) || matchPlatformBrowser(group, config))
                return true;
            return false;
        }
    }

    @Override
    public JSONObject updateTestrailPlan(String qaPlanRunId) throws AppContextException
    {
        try {
            Entity<IntegQAPlanRun> integQAPlanRunEntity = integContext.loadIntegQAPlanRun(qaPlanRunId);
            if(integQAPlanRunEntity==null || integQAPlanRunEntity.getData().getTestrailTestPlanId()==null)
            {
                return createTestrailPlan(qaPlanRunId);
            }
            
            Long testrailTestPlanId = integQAPlanRunEntity.getData().getTestrailTestPlanId();
            JSONObject oldTestrailTestPlan = testrailSystemCommandBus.submit(new GetTestPlanCommand(testrailTestPlanId)).getResult();
            
            Entity<com.amazon.core.qa.domain.entity.PlanRun> qaPlanRunEntity = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetPlanRunCommand(qaPlanRunId)).getResult();
            
            JSONObject updatedTestrailTestPlan = updateTestrailPlan(oldTestrailTestPlan, qaPlanRunEntity);
            return updatedTestrailTestPlan;
        } catch (Exception e) {
            throw new AppContextException(e);
        } 
    }
    
    private JSONObject updateTestrailPlan(JSONObject oldTestrailTestPlan, Entity<com.amazon.core.qa.domain.entity.PlanRun> qaPlanRunEntity)
    {
        return null;
    }

    @Override
    public JSONObject updateTestrailProject(String qaProjectId) throws AppContextException
    {
        try {
            Entity<IntegQAProject> integQAProjectEntity = integContext.loadIntegQAProject(qaProjectId);
            if(integQAProjectEntity==null || integQAProjectEntity.getData().getTestrailProjectId()==null)
            {
                return createTestrailProject(qaProjectId);
            }
            
            Long testrailProjectId = integQAProjectEntity.getData().getTestrailProjectId();
            JSONObject oldTestrailProject = testrailSystemCommandBus.submit(new GetProjectCommand(testrailProjectId)).getResult();
            
            Entity<com.amazon.core.qa.domain.entity.Project> qaProjectEntity = qaSystemCommandBus.submit(new com.amazon.core.qa.command.GetProjectCommand(qaProjectId)).getResult();
            
            JSONObject updatedTestrailProject = updateTestrailProject(oldTestrailProject, qaProjectEntity);
            return updatedTestrailProject;
        } catch (Exception e) {
            throw new AppContextException(e);
        } 
    }
    
    private JSONObject updateTestrailProject(JSONObject oldTestrailProject, Entity<com.amazon.core.qa.domain.entity.Project> qaProjectEntity)
    {  
        throw new NotImplementedException();
    }

    @Override
    public JSONObject deleteTestrailTestSuite(String qaProjectId, String planName) throws AppContextException
    {
        throw new NotImplementedException();
    }
}
