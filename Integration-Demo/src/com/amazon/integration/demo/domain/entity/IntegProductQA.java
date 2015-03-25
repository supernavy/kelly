package com.amazon.integration.demo.domain.entity;

import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.domain.Entity;


public class IntegProductQA
{
    public enum Status {New, Preparing, Testing, End};
    
    Entity<ProductQA> productQAInfo;
    Status status;
    Entity<IntegTestrailProject> integTestrailProjectInfo;
    
    public IntegProductQA(Entity<ProductQA> productQAInfo)
    {
        this(productQAInfo, Status.New, null);
    }
    
    private IntegProductQA(Entity<ProductQA> productQAInfo, Status status, Entity<IntegTestrailProject> integTestrailProjectInfo)
    {
        this.productQAInfo = productQAInfo;
        this.integTestrailProjectInfo = integTestrailProjectInfo;
        this.status = status;
    }

    public Entity<ProductQA> getProductQAInfo()
    {
        return productQAInfo;
    }

    public Status getStatus()
    {
        return status;
    }
    
    public String getName() {
        return String.format("IntegProductQA for %s", getProductQAInfo().getData().getName());
    }
    
    public IntegProductQA prepare()
    {
        return new IntegProductQA(getProductQAInfo(), Status.Preparing, null);
    }
    
    public IntegProductQA updateIntegTestrailProjectInfo(Entity<IntegTestrailProject> integTestrailProjectInfo)
    {
        return new IntegProductQA(getProductQAInfo(), Status.Testing, integTestrailProjectInfo);
    }
    
    public IntegProductQA end()
    {
        return new IntegProductQA(getProductQAInfo(), Status.End, getIntegTestrailProjectInfo());
    }

    public Entity<IntegTestrailProject> getIntegTestrailProjectInfo()
    {
        return integTestrailProjectInfo;
    }
}
