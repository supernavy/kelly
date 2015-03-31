package com.amazon.integration.demo.context;

import java.util.Set;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;

public interface MyTestrailContext
{
    public Entity<MyTestrailProject> newMyTestrailProject(String integProductQAEntityId) throws AppContextException;
    public Entity<MyTestrailProject> loadMyTestrailProject(String integTestrailProjectId) throws AppContextException;
    public Entity<MyTestrailProject> initMyTestrailProject(String integTestrailProjectId) throws AppContextException;
    public Entity<MyTestrailProject> updateMyTestrailProject(String integTestrailProjectId, MyTestrailProject myTestrailProject) throws AppContextException;
    public Entity<MyTestrailProject> endMyTestrailProject(String integTestrailProjectId) throws AppContextException;
    public Set<Entity<MyTestrailProject>> findMyTestrailProject(EntitySpec<MyTestrailProject> spec) throws AppContextException;
    
    public Entity<MyTestrailPlan> newMyTestrailPlan(String integBuildQAEntityId) throws AppContextException;
    public Entity<MyTestrailPlan> initMyTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Entity<MyTestrailPlan> updateMyTestrailPlan(String integTestrailPlanId, MyTestrailPlan myTestrailPlan) throws AppContextException;
    public Entity<MyTestrailPlan> startMyTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Entity<MyTestrailPlan> endMyTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Entity<MyTestrailPlan> loadMyTestrailPlan(String integTestrailPlanId) throws AppContextException;
    public Set<Entity<MyTestrailPlan>> findMyTestrailPlan(EntitySpec<MyTestrailPlan> spec) throws AppContextException;
    public void handleTestrailPlanComplete(Long testrailPlanId) throws AppContextException;
}
