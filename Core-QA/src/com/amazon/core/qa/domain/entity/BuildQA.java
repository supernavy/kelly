package com.amazon.core.qa.domain.entity;

import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.domain.Entity;

public class BuildQA
{
    public enum Status {New, InProgress, End};
    public enum Result {NA, Pass, Fail};
    
    Entity<Build> buildInfo;
    Entity<ProductQA> productQAInfo;
    
    Status status;
    Result result;
    
    public BuildQA(Entity<Build> buildInfo, Entity<ProductQA> productQAInfo)
    {
        this(buildInfo, productQAInfo, Status.New, Result.NA);
    }

    private BuildQA(Entity<Build> buildInfo, Entity<ProductQA> productQAInfo, Status status, Result result)
    {
        this.buildInfo = buildInfo;
        this.productQAInfo = productQAInfo;
        this.status = status;
        this.result = result;
    }

    public Entity<Build> getBuildInfo()
    {
        return buildInfo;
    }

    public Entity<ProductQA> getProductQAInfo()
    {
        return productQAInfo;
    }

    public Status getStatus()
    {
        return status;
    }
    public Result getResult()
    {
        return result;
    }
    
    public String getName() {
        return String.format("BuildQA for %s", getBuildInfo().getData().getName());
    }
    
    public BuildQA start() {
        return new BuildQA(getBuildInfo(), getProductQAInfo(), Status.InProgress, null);
    }
    
    public BuildQA end(Result result) {
        return new BuildQA(getBuildInfo(), getProductQAInfo(), Status.End, result);
    }
}
