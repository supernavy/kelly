package com.amazon.integration.demo.domain.entity;

import com.amazon.infra.domain.Entity;


public class ExternalSignoff
{
    public enum Status {New, Assigned, InProgress, End};
    
    Entity<IntegBuildQA> integBuildQAInfo;
    String featureName;
    Owner owner;
    Status status;
    Result result;
    
    public ExternalSignoff(Entity<IntegBuildQA> integBuildQAInfo, String featureName)
    {
        this(integBuildQAInfo, featureName, null, Status.New, null);
    }

    private ExternalSignoff(Entity<IntegBuildQA> integBuildQAInfo, String featureName, Owner owner, Status status, Result result)
    {
        this.integBuildQAInfo = integBuildQAInfo;
        this.featureName = featureName;
        this.owner = owner;
        this.status = status;
        this.result = result;
    }

    public Entity<IntegBuildQA> getIntegBuildQAInfo()
    {
        return integBuildQAInfo;
    }

    public String getFeatureName()
    {
        return featureName;
    }

    public Owner getOwner()
    {
        return owner;
    }

    public Status getStatus()
    {
        return status;
    }
    
    public Result getResult()
    {
        return result;
    }
    
    public ExternalSignoff end()
    {
        return new ExternalSignoff(getIntegBuildQAInfo(), getFeatureName(), getOwner(), Status.End, getResult());
    }
    
    public ExternalSignoff sendRequest()
    {
        return new ExternalSignoff(getIntegBuildQAInfo(), getFeatureName(), getOwner(), Status.InProgress, getResult());
    }
    
    public ExternalSignoff assign(Owner owner)
    {
        return new ExternalSignoff(getIntegBuildQAInfo(), getFeatureName(), owner, Status.Assigned, getResult());
    }
    
    public static class Owner {
        String name;
        String email;
        public Owner(String name, String email)
        {
            this.name = name;
            this.email = email;
        }
        public String getName()
        {
            return name;
        }
        public String getEmail()
        {
            return email;
        }
    }
   
    public static class Result {
        boolean pass;
        String notes;
        public Result(boolean pass, String notes)
        {
            this.pass = pass;
            this.notes = notes;
        }
        public boolean isPass()
        {
            return pass;
        }
        public String getNotes()
        {
            return notes;
        }
    }
}
