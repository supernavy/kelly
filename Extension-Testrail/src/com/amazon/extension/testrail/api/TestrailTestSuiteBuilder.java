package com.amazon.extension.testrail.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class TestrailTestSuiteBuilder
{
    TestrailProjectContext projectContext;
    JSONObject suite;

    public TestrailTestSuiteBuilder(TestrailProjectContext projectContext)
    {
        this.projectContext = projectContext;
        suite = new JSONObject();
    }
    
    public TestrailProjectContext getProjectContext()
    {
        return projectContext;
    }
        
    public TestrailTestSuiteBuilder setName(String name)
    {
        suite.put(TestrailAPI.Key.Name, name);
        return this;
    }
    
    public TestrailTestSuiteBuilder addEntry(JSONObject entry)
    {
        JSONArray entries = (JSONArray) suite.get(TestrailAPI.Key.Entries);
        if(entries == null)
        {
            entries = new JSONArray();
            suite.put(TestrailAPI.Key.Entries, entries);
        }
        entries.add(entry);
        
        return this;
    }
    
    public TestrailTestSuiteBuilder setDescription(String description)
    {
        suite.put(TestrailAPI.Key.Description, description);
        return this;
    }
    
    public JSONObject build()
    {
        return suite;
    }
    
    public TestPlanEntryBuilder newTestPlanEntryBuilder(String name, JSONObject suite, JSONArray cases)
    {
        return new TestPlanEntryBuilder(this.getProjectContext(), name, suite, cases);
    }
    
    public class TestPlanEntryBuilder {
        TestrailRootContext testrailContext;
        TestrailProjectContext projectContext;
        String name;
        JSONObject suite;
        JSONArray cases;
        
        JSONObject entry;
      
        public TestPlanEntryBuilder(TestrailProjectContext projectContext, String name, JSONObject suite, JSONArray cases)
        {
            this.projectContext = projectContext;
            this.name = name;
            this.suite = suite;
            this.cases = cases;
            entry = new JSONObject();
            entry.put(TestrailAPI.Key.Suite_Id, suite.get(TestrailAPI.Key.Id));
            entry.put(TestrailAPI.Key.Name, name);
        }
        
        public TestPlanEntryBuilder filterCaseIds(TestCaseSelector selector)
        {
            JSONArray selectedCaseIds = new JSONArray();
            for(Object o: cases)
            {
                JSONObject testcase = (JSONObject) o;
                if(selector.matches(testcase))
                {
                    selectedCaseIds.add(testcase.get(TestrailAPI.Key.Id));
                }
            }
            entry.put(TestrailAPI.Key.Include_All, false);
            entry.put(TestrailAPI.Key.Case_Ids, selectedCaseIds);
            return this;
        }
        
        public TestPlanEntryBuilder filterConfigIds(ConfigurationSelector selector)
        {
            JSONArray selectedConfigIds = new JSONArray();
            for(Object o: projectContext.getTestrailConfigs())
            {
                JSONObject group = (JSONObject) o;
                
                for (Object c : (JSONArray) group.get(TestrailAPI.Key.Configs)) {
                    JSONObject conf = (JSONObject) c;
                    if (selector.select(group, conf)) {
                        selectedConfigIds.add(conf.get(TestrailAPI.Key.Id));
                    }
                }
            }
            
            entry.put(TestrailAPI.Key.Config_Ids, selectedConfigIds);
            JSONObject run = new JSONObject();
            run.put(TestrailAPI.Key.Config_Ids, selectedConfigIds);
            JSONArray runs = new JSONArray();
            runs.add(run);
            entry.put(TestrailAPI.Key.Runs, runs);
            return this;
        }

        public String getName()
        {
            return name;
        }
        
        public JSONObject getSuite()
        {
            return suite;
        }
        
        public JSONObject build()
        {          
            return entry;
        }
    }
}
