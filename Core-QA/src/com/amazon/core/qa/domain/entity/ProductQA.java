package com.amazon.core.qa.domain.entity;

import java.util.HashMap;
import java.util.Map;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.infra.domain.Entity;

public class ProductQA
{
    public enum Status {New, Ready, End};
    
    Entity<Product> productInfo;
    Status status;
    
    Map<String,Plan> plans = new HashMap<String, Plan>();
    
    public ProductQA(Entity<Product> productinfo)
    {
        this(productinfo, Status.New, null);
    }
    
    private ProductQA(Entity<Product> productInfo, Status status, Map<String,Plan> plans)
    {
        this.productInfo = productInfo;
        this.status = status;
        if(plans!=null)
        {
            this.plans.putAll(plans);
        }
    }
    
    public Entity<Product> getProductInfo()
    {
        return productInfo;
    }

    public Status getStatus()
    {
        return status;
    }
    
    public String getName() {
        return String.format("QA for %s", getProductInfo().getData().getName());
    }
    
    public Map<String, Plan> getPlans()
    {
        return new HashMap<String, Plan>(plans);
    }
    
    public ProductQA ready()
    {
        return new ProductQA(getProductInfo(), Status.Ready, getPlans());
    }
    
    public ProductQA end()
    {
        return new ProductQA(getProductInfo(), Status.End, getPlans());
    }
    
    public ProductQA addPlan(Plan plan)
    {
        Map<String,Plan> ps = getPlans();
        ps.put(plan.getName(), plan);
        return new ProductQA(getProductInfo(), getStatus(), ps);
    }
}
