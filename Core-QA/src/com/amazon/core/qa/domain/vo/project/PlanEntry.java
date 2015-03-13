package com.amazon.core.qa.domain.vo.project;

import com.amazon.core.qa.domain.entity.TestSuite;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.infra.domain.Entity;

public class PlanEntry {
    Entity<TestSuite> testSuiteInfo;
    String name;
    Locale locale;
    Platform platform;
    Browser browser;
    Priority priority;
    View view;
    
    public PlanEntry(String name, Entity<TestSuite> testSuiteInfo, Locale locale, Platform platform, Browser browser, View view, Priority priority)
    {
        this.testSuiteInfo = testSuiteInfo;
        this.locale = locale;
        this.platform = platform;
        this.browser = browser;
        this.view = view;
        this.priority = priority;
        this.name = name;
    }
    
    public Entity<TestSuite> getTestSuiteInfo()
    {
        return testSuiteInfo;
    }
    
    public Locale getLocale()
    {
        return locale;
    }
    
    public Platform getPlatform()
    {
        return platform;
    }
    
    public Priority getPriority()
    {
        return priority;
    }
    
    public Browser getBrowser()
    {
        return browser;
    }
    
    public View getView()
    {
        return view;
    }
    public String getName()
    {
        return name;
    }
}