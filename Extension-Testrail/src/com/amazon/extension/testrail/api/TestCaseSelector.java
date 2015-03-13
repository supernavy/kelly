package com.amazon.extension.testrail.api;

import org.json.simple.JSONObject;

public interface TestCaseSelector
{
    public boolean matches(JSONObject testCase);
}
