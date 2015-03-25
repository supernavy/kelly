package com.amazon.core.qa.system;

import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleQASystem extends SimpleAppSystemImpl
{
    public SimpleQASystem(String name, Layer layer, AppSystem pmSystem, AppSystem rmSystem) throws AppSystemException
    {
        super(name, layer);
        
        setDependency(QASystem.System_PM, pmSystem);
        setDependency(QASystem.System_RM, rmSystem);
    }
}
