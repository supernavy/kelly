package com.amazon.integration.demo.context;

import java.util.Set;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;

public interface ExternalSignoffContext
{    
    public Entity<ExternalSignoff> newExternalSignoff(String integBuildQAId, String featureName) throws AppContextException;
    public Entity<ExternalSignoff> sendRequestExternalSignoff(String externalSignoffId) throws AppContextException;
    public Entity<ExternalSignoff> assignExternalSignoff(String externalSignoffId,ExternalSignoff.Owner owner) throws AppContextException;
    public Entity<ExternalSignoff> endExternalSignoff(String externalSignoffId) throws AppContextException;
    public Entity<ExternalSignoff> loadExternalSignoff(String externalSignoffId) throws AppContextException;
    public Set<Entity<ExternalSignoff>> findExternalSignoff(EntitySpec<ExternalSignoff> spec) throws AppContextException;
}
