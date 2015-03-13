package com.amazon.core.qa.system;

import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleQASystem extends SimpleAppSystemImpl
{
    public SimpleQASystem(String name, Layer layer, AppSystem pmSystem, AppSystem rmSystem) throws AppSystemException
    {
        super(name, layer);
        getRepository(QASystem.Repository_Project);
        getRepository(QASystem.Repository_PlanRun);
        getRepository(QASystem.Repository_TestCase);
        getRepository(QASystem.Repository_TestSuite);
        
        setDependency(QASystem.System_PM, pmSystem);
        setDependency(QASystem.System_RM, rmSystem);
    }
}
