package com.amazon.integration.demo.context.impl;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.productqa.PlanEntry;
import com.amazon.extension.testrail.api.TestCaseSelector;
import com.amazon.extension.testrail.api.TestrailAPI;

public class TestrailCaseSelector implements TestCaseSelector
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

