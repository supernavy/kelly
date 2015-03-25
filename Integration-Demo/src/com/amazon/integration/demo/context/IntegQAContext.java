package com.amazon.integration.demo.context;

import java.util.Set;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;
import com.amazon.integration.demo.domain.entity.IntegProductQA;

public interface IntegQAContext
{
    public Entity<IntegProductQA> newIntegProductQA(String productQAEntityId) throws AppContextException;
    public Entity<IntegProductQA> loadIntegProductQA(String integProductQAId) throws AppContextException;
    public Entity<IntegProductQA> prepareIntegProductQA(String integProductQAId) throws AppContextException;
    public Entity<IntegProductQA> updateIntegProductQAWithTestrailProjecct(String integProductQAId, String integTestrailProjectId) throws AppContextException;
    public Entity<IntegProductQA> endIntegProductQA(String integProductQAId) throws AppContextException;
    public Set<Entity<IntegProductQA>> findIntegProductQA(EntitySpec<IntegProductQA> spec) throws AppContextException;
    
    public Entity<IntegBuildQA> newIntegBuildQA(String buildQAEntityId) throws AppContextException;
    public Entity<IntegBuildQA> loadIntegBuildQA(String integBuildQAEntityId) throws AppContextException;
    public Entity<IntegBuildQA> prepareIntegBuildQA(String integBuildQAEntityId) throws AppContextException;
    public Entity<IntegBuildQA> updateIntegBuildQAWithTestrailPlan(String integBuildQAEntityId, String integTestrailPlanId) throws AppContextException;
    public Entity<IntegBuildQA> endIntegBuildQA(String integBuildQAEntityId) throws AppContextException;
    public Set<Entity<IntegBuildQA>> findIntegBuildQA(EntitySpec<IntegBuildQA> spec) throws AppContextException;
}
