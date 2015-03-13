package com.amazon.core.rm.context;

import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;

public interface BuildContext
{
    public Entity<Build> createBuild(String pmProductId, String buildName, String patchName) throws AppContextException;
    public Entity<Build> loadBuild(String id) throws AppContextException;
}
