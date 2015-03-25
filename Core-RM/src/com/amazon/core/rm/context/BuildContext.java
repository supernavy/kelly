package com.amazon.core.rm.context;

import java.util.Set;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public interface BuildContext
{
    public Entity<Build> createBuild(String pmProductId, String buildName, String patchName) throws AppContextException;
    public Entity<Build> loadBuild(String id) throws AppContextException;
    public Set<Entity<Build>> findBuild(EntitySpec<Build> spec) throws AppContextException;
}
