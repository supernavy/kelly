package com.amazon.extension.testrail.api;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.infra.restapi.DataType;
import com.amazon.infra.restapi.GetMethod;
import com.amazon.infra.restapi.PostMethod;
import com.amazon.infra.restapi.PrimitiveDataType;
import com.amazon.infra.restapi.type.EnumDataType;
import com.amazon.infra.restapi.type.JSONArrayDataType;
import com.amazon.infra.restapi.type.JSONObjectDataType;

public class TestrailAPI
{
    public static class Type
    {        
        public static JSONObjectDataType Case;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Priority_Id, PrimitiveDataType.LONG);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            Case = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> Cases = new JSONArrayDataType<JSONObject>(Type.Case);

        public static JSONObjectDataType Test;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Case_Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Run_Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Status_Id, PrimitiveDataType.LONG);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            Test = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> Tests = new JSONArrayDataType<JSONObject>(Type.Test);
        
        public static JSONObjectDataType Config;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Group_Id, PrimitiveDataType.LONG);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            Config = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> Configs = new JSONArrayDataType<JSONObject>(Type.Config);

        public static JSONObjectDataType ConfigGroup;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Project_Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Configs, Type.Configs);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            ConfigGroup = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> ConfigGourps = new JSONArrayDataType<JSONObject>(Type.ConfigGroup);

        public static JSONObjectDataType Priority;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Priority, PrimitiveDataType.LONG);
            optionalValueType.put(Key.Is_Default, PrimitiveDataType.BOOL);
            optionalValueType.put(Key.Short_Name, PrimitiveDataType.STRING);

            Priority = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> Priorities = new JSONArrayDataType<JSONObject>(Type.Priority);

        public static JSONObjectDataType Project;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Suite_Mode, new EnumDataType<Long>(PrimitiveDataType.LONG, new Long[] { 1L, 2L, 3L }));
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Announcement, PrimitiveDataType.STRING);
            optionalValueType.put(Key.Completed_On, PrimitiveDataType.LONG);
            optionalValueType.put(Key.Is_Completed, PrimitiveDataType.BOOL);
            optionalValueType.put(Key.Show_Announcement, PrimitiveDataType.BOOL);
            optionalValueType.put(Key.URL, PrimitiveDataType.STRING);

            Project = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONObjectDataType Suite;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Is_Baseline, PrimitiveDataType.BOOL);
            optionalValueType.put(Key.Is_Master, PrimitiveDataType.BOOL);
            optionalValueType.put(Key.Completed_On, PrimitiveDataType.LONG);
            optionalValueType.put(Key.Description, PrimitiveDataType.STRING);
            optionalValueType.put(Key.Name, PrimitiveDataType.STRING);
            optionalValueType.put(Key.Project_Id, PrimitiveDataType.LONG);
            optionalValueType.put(Key.Is_Completed, PrimitiveDataType.BOOL);
            optionalValueType.put(Key.URL, PrimitiveDataType.STRING);

            Suite = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONObjectDataType PlanRequestEntryRun;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Case_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            requiredValueType.put(Key.Config_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            PlanRequestEntryRun = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> PlanRequestEntryRuns = new JSONArrayDataType<JSONObject>(Type.PlanRequestEntryRun);

        public static JSONObjectDataType PlanRequestEntry;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Suite_Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Include_All, PrimitiveDataType.BOOL);
            requiredValueType.put(Key.Case_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            requiredValueType.put(Key.Config_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            requiredValueType.put(Key.Runs, Type.PlanRequestEntryRuns);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            PlanRequestEntry = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> PlanRequestEntries = new JSONArrayDataType<JSONObject>(PlanRequestEntry);

        public static JSONObjectDataType PlanRequest;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Entries, Type.PlanRequestEntries);

            PlanRequest = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONObjectDataType PlanEntryRun;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Case_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            requiredValueType.put(Key.Config_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            PlanEntryRun = new JSONObjectDataType(requiredValueType, optionalValueType);
        }
        public static JSONArrayDataType<JSONObject> PlanEntryRuns = new JSONArrayDataType<JSONObject>(Type.PlanEntryRun);
        public static JSONObjectDataType PlanEntry;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Suite_Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Include_All, PrimitiveDataType.BOOL);
            requiredValueType.put(Key.Case_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            requiredValueType.put(Key.Config_Ids, new JSONArrayDataType<Long>(PrimitiveDataType.LONG));
            requiredValueType.put(Key.Runs, Type.PlanRequestEntryRuns);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            PlanEntry = new JSONObjectDataType(requiredValueType, optionalValueType);
        }
        public static JSONArrayDataType<JSONObject> PlanEntries = new JSONArrayDataType<JSONObject>(PlanEntry);
        public static JSONObjectDataType Plan;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Entries, PlanEntries);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();

            Plan = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONObjectDataType AddResultForCaseRequest;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Status_Id, new EnumDataType<Long>(PrimitiveDataType.LONG, new Long[] { 1L, 2L, 3L, 4L, 5L }));
            requiredValueType.put(Key.Comment, PrimitiveDataType.STRING);
//            requiredValueType.put(Key.Version, PrimitiveDataType.STRING);
//            requiredValueType.put(Key.Elapsed, PrimitiveDataType.STRING);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Defects, PrimitiveDataType.STRING);

            AddResultForCaseRequest = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONObjectDataType AddResultsForCaseRequest;
        static {
            JSONArrayDataType<JSONObject> resultsType;
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Status_Id, new EnumDataType<Long>(PrimitiveDataType.LONG, new Long[] { 1L, 2L, 3L, 4L, 5L }));
            requiredValueType.put(Key.Comment, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Version, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Elapsed, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Assignedto_Id, PrimitiveDataType.LONG);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Defects, PrimitiveDataType.STRING);

            resultsType = new JSONArrayDataType<JSONObject>(new JSONObjectDataType(requiredValueType, optionalValueType));

            requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Results, resultsType);
            optionalValueType = new HashMap<String, DataType<?>>();
            AddResultsForCaseRequest = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONObjectDataType Result;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Test_Id, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Status_Id, new EnumDataType<Long>(PrimitiveDataType.LONG, new Long[] { 1L, 2L, 3L, 4L, 5L }));
            requiredValueType.put(Key.Version, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Elapsed, PrimitiveDataType.STRING);
            requiredValueType.put(Key.Created_By, PrimitiveDataType.LONG);
            requiredValueType.put(Key.Created_On, PrimitiveDataType.LONG);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            optionalValueType.put(Key.Defects, PrimitiveDataType.STRING);
            optionalValueType.put(Key.Comment, PrimitiveDataType.STRING);
            optionalValueType.put(Key.Assignedto_Id, PrimitiveDataType.LONG);

            Result = new JSONObjectDataType(requiredValueType, optionalValueType);
        }

        public static JSONArrayDataType<JSONObject> Results = new JSONArrayDataType<JSONObject>(Result);
        
        public static JSONObjectDataType AddProjectRequest;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            AddProjectRequest = new JSONObjectDataType(requiredValueType, optionalValueType);
        }
        
        public static JSONObjectDataType AddSuiteRequest;
        static {
            Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
            requiredValueType.put(Key.Name, PrimitiveDataType.STRING);
            Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
            AddSuiteRequest = new JSONObjectDataType(requiredValueType, optionalValueType);
        }
    }

    public static class Method
    {
        public static GetMethod<JSONArray> GET_CASES;
        static {
            /*
             * initialize GET_CASE
             */
            GET_CASES = new GetMethod<JSONArray>("/get_cases", 1, 1);
            GET_CASES.addRequestFilter(Key.Suite_Id, PrimitiveDataType.LONG);
            GET_CASES.setReturnDataType(Type.Cases);
        }

        public static GetMethod<JSONArray> GET_CONFIGURATIONS;
        static {
            /*
             * initialize GET_CONFIGURATIONS
             */
            GET_CONFIGURATIONS = new GetMethod<JSONArray>("/get_configs", 1, 1);
            GET_CONFIGURATIONS.setReturnDataType(Type.ConfigGourps);
        }

        public static GetMethod<JSONArray> GET_PRIORITIES;
        static {
            /*
             * initialize GET_PRIORITIES
             */
            GET_PRIORITIES = new GetMethod<JSONArray>("/get_priorities", 0, 0);
            GET_PRIORITIES.setReturnDataType(Type.Priorities);
        }

        public static GetMethod<JSONObject> GET_PROJECT;
        static {
            /*
             * initialize GET_PROJECT
             */
            GetMethod<JSONObject> m = new GetMethod<JSONObject>("/get_project", 1, 1);
            m.setReturnDataType(Type.Project);
            GET_PROJECT = m;
        }

        public static GetMethod<JSONObject> GET_SUITE;
        static {
            /*
             * initialize GET_SUITE
             */
            GET_SUITE = new GetMethod<JSONObject>("/get_suite", 1, 1);
            GET_SUITE.setReturnDataType(Type.Suite);
        }
        
        public static GetMethod<JSONObject> GET_PLAN;
        static {
            /*
             * initialize GET_SUITE
             */
            GET_PLAN = new GetMethod<JSONObject>("/get_plan", 1, 1);
            GET_PLAN.setReturnDataType(Type.Plan);
        }
        
        public static GetMethod<JSONArray> GET_TESTS;
        static {
            /*
             * initialize GET_TESTS
             */
            GET_TESTS = new GetMethod<JSONArray>("/get_tests", 1, 1); //need runId
            GET_TESTS.addRequestFilter(TestrailAPI.Key.Status_Id, PrimitiveDataType.LONG);
            GET_TESTS.setReturnDataType(Type.Tests);
        }

        public static PostMethod<JSONObject> ADD_PLAN;
        static {
            /*
             * initialize ADD_PLAN
             */
            ADD_PLAN = new PostMethod<JSONObject>("/add_plan", 1, 1, Type.PlanRequest);//project_id
            ADD_PLAN.setReturnDataType(Type.Plan);
        }
        
        public static PostMethod<JSONObject> DELETE_PLAN;
        static {
            /*
             * initialize DELETE_PLAN
             */
            DELETE_PLAN = new PostMethod<JSONObject>("/delete_plan", 1, 1, PrimitiveDataType.NULL);//plan_id
            DELETE_PLAN.setReturnDataType(JSONObjectDataType.EMPTY);
        }

        public static PostMethod<JSONObject> ADD_RESULT_FOR_CASE;
        static {
            ADD_RESULT_FOR_CASE = new PostMethod<JSONObject>("/add_result_for_case", 2, 2, Type.AddResultForCaseRequest);
            ADD_RESULT_FOR_CASE.setReturnDataType(Type.Result);
        }

        public static PostMethod<JSONArray> ADD_RESULTS_FOR_CASE;
        static {
            ADD_RESULTS_FOR_CASE = new PostMethod<JSONArray>("/add_results_for_case", 2, 2, Type.AddResultsForCaseRequest);
            ADD_RESULTS_FOR_CASE.setReturnDataType(Type.Results);
        }
        
        public static PostMethod<JSONObject> ADD_PROJECT;
        static {
            /*
             * initialize ADD_PROJECT
             */
            ADD_PROJECT = new PostMethod<JSONObject>("/add_project", 0, 0, Type.AddProjectRequest);
            ADD_PROJECT.setReturnDataType(Type.Project);
        }
        
        public static PostMethod<JSONObject> DELETE_PROJECT;
        static {
            /*
             * initialize ADD_PROJECT
             */
            DELETE_PROJECT = new PostMethod<JSONObject>("/delete_project", 1, 1, PrimitiveDataType.NULL);
            DELETE_PROJECT.setReturnDataType(JSONObjectDataType.EMPTY);
        }
        
        public static PostMethod<JSONObject> ADD_SUITE;
        static {
            /*
             * initialize ADD_SUITE
             */
            ADD_SUITE = new PostMethod<JSONObject>("/add_suite", 1, 1, Type.AddSuiteRequest);//projectId
            ADD_SUITE.setReturnDataType(Type.Suite);
        }
        
        public static PostMethod<JSONObject> UPDATE_SUITE;
        static {
            /*
             * initialize UPDATE_SUITE
             */
            UPDATE_SUITE = new PostMethod<JSONObject>("/update_suite", 1, 1, Type.AddSuiteRequest);//suiteid
            UPDATE_SUITE.setReturnDataType(Type.Suite);
        }
        
        public static PostMethod<JSONObject> DELETE_SUITE;
        static {
            /*
             * initialize DELETE_PLAN
             */
            DELETE_SUITE = new PostMethod<JSONObject>("/delete_suite", 1, 1, PrimitiveDataType.NULL);//suite id
            DELETE_SUITE.setReturnDataType(JSONObjectDataType.EMPTY);
        }
    }

    public static class Key
    {
        public static String Results = "results";
        public static String Case_Id = "case_id";
        public static String Created_By = "created_by";
        public static String Created_On = "created_on";
        public static String Name = "name";
        public static String Description = "description";
        public static String Entries = "entries";
        public static String Milestone_Id = "milestone_id";
        public static String Id = "id";
        public static String Project_Id = "project_id";
        public static String Suite_Id = "suite_id";
        public static String Is_Completed = "is_completed";
        public static String Announcement = "announcement";
        public static String Completed_On = "completed_on";
        public static String Show_Announcement = "show_announcement";
        public static String Suite_Mode = "suite_mode";
        public static String URL = "url";
        public static String Priority_Id = "priority_id";
        public static String Custom_Prefix = "custom_";
        public static String Group_Id = "group_id";
        public static String Configs = "configs";
        public static String Case_Ids = "case_ids";
        public static String Config_Ids = "config_ids";
        public static String Runs = "runs";
        public static String Include_All = "include_all";
        public static String Priority = "priority";
        public static String Is_Default = "is_default";
        public static String Short_Name = "short_name";
        public static String Is_Baseline = "is_baseline";
        public static String Is_Master = "is_master";
        public static String Status_Id = "status_id";
        public static String Comment = "comment";
        public static String Version = "version";
        public static String Elapsed = "elapsed";
        public static String Defects = "defects";
        public static String Assignedto_Id = "assignedto_id";
        public static String Test_Id = "test_id";
        public static String Run_Id = "run_id";
        public static String Passed_Count = "passed_count";
        public static String Failed_Count = "failed_count";
        public static String Retest_Count = "retest_count";
        public static String Untest_Count = "untested_count";
        public static String Blocked_Count = "blocked_count";
    }
}
