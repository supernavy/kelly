package com.amazon.integration.demo.context;

import java.util.Set;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public interface IntegTestrailContext
{
    public Entity<IntegTestrailProject> newIntegTestrailProject(String integProductQAEntityId) throws AppContextException;
    public Entity<IntegTestrailProject> loadIntegTestrailProject(String integTestrailProjectId) throws AppContextException;
    public Entity<IntegTestrailProject> initIntegTestrailProject(String integTestrailProjectId) throws AppContextException;
    public Entity<IntegTestrailProject> endIntegTestrailProject(String integTestrailProjectId) throws AppContextException;
    public Set<Entity<IntegTestrailProject>> findIntegTestrailProject(EntitySpec<IntegTestrailProject> spec) throws AppContextException;
    
    public Entity<IntegTestrailPlan> newIntegTestrailPlan(String integBuildQAEntityId) throws AppContextException;
    public Entity<IntegTestrailPlan> initIntegTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Entity<IntegTestrailPlan> startIntegTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Entity<IntegTestrailPlan> endIntegTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Entity<IntegTestrailPlan> loadIntegTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Set<Entity<IntegTestrailPlan>> findIntegTestrailPlan(EntitySpec<IntegTestrailPlan> spec) throws AppContextException;
    public void handleTestrailPlanComplete(Long testrailPlanId) throws AppContextException;
}
