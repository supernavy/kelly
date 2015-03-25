package com.amazon.integration.demo.context.impl;

import org.json.simple.JSONObject;
import com.amazon.core.qa.domain.vo.productqa.PlanEntry;
import com.amazon.extension.testrail.api.ConfigurationSelector;
import com.amazon.extension.testrail.api.TestrailAPI;

public class TestrailConfigurationsSelector implements ConfigurationSelector
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