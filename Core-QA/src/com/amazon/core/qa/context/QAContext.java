package com.amazon.core.qa.context;

import java.util.Set;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public interface QAContext
{
    public Entity<ProductQA> newProductQA(String pmProductId) throws AppContextException;
    public Entity<ProductQA> loadProductQA(String productQAId) throws AppContextException;
    public Entity<ProductQA> addPlanProductQA(String productQAId, Plan plan) throws AppContextException;
    public Entity<ProductQA> readyProductQA(String productQAId) throws AppContextException;
    public Entity<ProductQA> endProductQA(String productQAId) throws AppContextException;
    public Set<Entity<ProductQA>> findProductQA(EntitySpec<ProductQA> spec) throws AppContextException;
    
    public Entity<BuildQA> newBuildQA(String rmBuildId) throws AppContextException;
    public Entity<BuildQA> loadBuildQA(String buildQAId) throws AppContextException;
    public Entity<BuildQA> startBuildQA(String buildQAId) throws AppContextException;
    public Entity<BuildQA> endBuildQA(String buildQAId, BuildQA.Result result) throws AppContextException;
    public Set<Entity<BuildQA>> findBuildQA(EntitySpec<BuildQA> spec) throws AppContextException;
}
