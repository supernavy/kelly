package com.amazon.integration.demo.context;

import java.util.Set;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.entity.MyProductQA;

public interface MyQAContext
{
    public Entity<MyProductQA> newMyProductQA(String productQAEntityId) throws AppContextException;
    public Entity<MyProductQA> loadMyProductQA(String integProductQAId) throws AppContextException;
    public Entity<MyProductQA> prepareMyProductQA(String integProductQAId) throws AppContextException;
    public Entity<MyProductQA> updateMyProductQAWithTestrailProjecct(String integProductQAId, String integTestrailProjectId) throws AppContextException;
    public Entity<MyProductQA> endMyProductQA(String integProductQAId) throws AppContextException;
    public Set<Entity<MyProductQA>> findMyProductQA(EntitySpec<MyProductQA> spec) throws AppContextException;
    
    public Entity<MyBuildQA> newMyBuildQA(String buildQAEntityId) throws AppContextException;
    public Entity<MyBuildQA> loadMyBuildQA(String integBuildQAEntityId) throws AppContextException;
    public Entity<MyBuildQA> prepareMyBuildQA(String integBuildQAEntityId) throws AppContextException;
    public Entity<MyBuildQA> updateMyBuildQA(String integBuildQAEntityId, MyBuildQA myBuildQA) throws AppContextException;
    public Entity<MyBuildQA> updateMyBuildQAWithTestrailPlan(String integBuildQAEntityId, String integTestrailPlanId) throws AppContextException;
    public Entity<MyBuildQA> endMyBuildQA(String integBuildQAEntityId) throws AppContextException;
    public Set<Entity<MyBuildQA>> findMyBuildQA(EntitySpec<MyBuildQA> spec) throws AppContextException;
}
