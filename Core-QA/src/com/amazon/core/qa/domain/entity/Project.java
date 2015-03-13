package com.amazon.core.qa.domain.entity;

import java.util.HashMap;
import java.util.Map;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.domain.vo.project.Plan;
import com.amazon.infra.domain.Entity;

public class Project
{
    Entity<Product> productInfo;
    String name;
    Map<String,Plan> plans = new HashMap<String, Plan>();
    
    public Project(Entity<Product> productInfo, String name)
    {
        this(productInfo, name, null);
    }
    
    public Project(Entity<Product> productInfo, String name, Map<String,Plan> plans)
    {
        this.productInfo = productInfo;
        this.name = name;
        if(plans!=null)
            this.plans.putAll(plans);
    }
    
    public String getName()
    {
        return name;
    }
    
    public Entity<Product> getProductInfo()
    {
        return productInfo;
    }
    
    public Map<String, Plan> getPlans()
    {
        return new HashMap<String,Plan>(plans);
    }
    
    public Project addPlan(Plan plan)
    {
        Map<String,Plan> newPlans = getPlans();
        newPlans.put(plan.getName(), plan);
        
        Project newProject = new Project(productInfo, name, newPlans);

        return newProject;
    }
    
    public Project updatePlan(String planName, Plan plan)
    {
        Map<String,Plan> newPlans = getPlans();
        if(newPlans.containsKey(planName))
        {
            newPlans.putAll(getPlans());
            newPlans.put(planName, plan);
            
            Project newProject = new Project(productInfo, name, newPlans);
    
            return newProject;
        }
        return this;
    }
}
