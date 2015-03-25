package com.amazon.core.rm.system;

import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleRMSystem extends SimpleAppSystemImpl
{
    public SimpleRMSystem(String name, Layer layer, AppSystem pmSystem) throws AppSystemException
    {
        super(name, layer);
        getRepository(RMSystem.Repository_Build);
        
        setDependency(RMSystem.System_PM, pmSystem);
    }
}
