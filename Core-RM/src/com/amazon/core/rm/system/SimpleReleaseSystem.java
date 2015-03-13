package com.amazon.core.rm.system;

import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleReleaseSystem extends SimpleAppSystemImpl
{
    public SimpleReleaseSystem(String name, Layer layer, AppSystem pmSystem) throws AppSystemException
    {
        super(name, layer);
        getRepository(ReleaseSystem.Repository_Build);
        
        setDependency(ReleaseSystem.System_PM, pmSystem);
    }
}
