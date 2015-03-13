package com.amazon.extension.testrail.api;

import org.json.simple.JSONObject;

public interface ConfigurationSelector
{
    public boolean select(JSONObject group, JSONObject config);
}
