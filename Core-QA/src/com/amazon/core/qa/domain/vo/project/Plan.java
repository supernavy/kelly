package com.amazon.core.qa.domain.vo.project;

import java.util.Map;
import java.util.TreeMap;

public class Plan
{
    /**
     * 
     */
    String name;
    String entryBaseName;

    Map<String, PlanEntry> planEntries = new TreeMap<String, PlanEntry>();

    public Plan(String name, String runBaseName)
    {
        this(name, runBaseName, null);
    }

    public Plan(String name, String entryBaseName, Map<String, PlanEntry> planEntries)
    {
        this.name = name;
        this.entryBaseName = entryBaseName;
        if(planEntries != null)
        {
            this.planEntries.putAll(planEntries);
        }
    }
    
    public Plan addEntry(PlanEntry planEntry)
    {
        Map<String, PlanEntry> newPlanEntries = getPlanEntries();
        newPlanEntries.put(planEntry.getName(), planEntry);
        return new Plan(getName(), getEntryBaseName(), newPlanEntries);
    }

    public Map<String, PlanEntry> getPlanEntries()
    {
        return new TreeMap<String, PlanEntry>(planEntries);
    }

    public String getName()
    {
        return name;
    }

    public String getEntryBaseName()
    {
        return entryBaseName;
    }
}