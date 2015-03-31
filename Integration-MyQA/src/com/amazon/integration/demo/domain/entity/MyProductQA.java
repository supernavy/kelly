package com.amazon.integration.demo.domain.entity;

import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.domain.Entity;


public class MyProductQA
{
    public enum Status {New, Preparing, Testing, End};
    
    Entity<ProductQA> productQAInfo;
    Status status;
    Entity<MyTestrailProject> integTestrailProjectInfo;
    
    public MyProductQA(Entity<ProductQA> productQAInfo)
    {
        this(productQAInfo, Status.New, null);
    }
    
    private MyProductQA(Entity<ProductQA> productQAInfo, Status status, Entity<MyTestrailProject> integTestrailProjectInfo)
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
        return String.format("MyProductQA for %s", getProductQAInfo().getData().getName());
    }
    
    public MyProductQA prepare()
    {
        return new MyProductQA(getProductQAInfo(), Status.Preparing, null);
    }
    
    public MyProductQA updateMyTestrailProjectInfo(Entity<MyTestrailProject> integTestrailProjectInfo)
    {
        return new MyProductQA(getProductQAInfo(), Status.Testing, integTestrailProjectInfo);
    }
    
    public MyProductQA end()
    {
        return new MyProductQA(getProductQAInfo(), Status.End, getMyTestrailProjectInfo());
    }

    public Entity<MyTestrailProject> getMyTestrailProjectInfo()
    {
        return integTestrailProjectInfo;
    }
}
