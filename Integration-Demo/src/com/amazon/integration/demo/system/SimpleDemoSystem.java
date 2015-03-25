package com.amazon.integration.demo.system;

import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleDemoSystem extends SimpleAppSystemImpl
{

    public SimpleDemoSystem(String name, Layer layer, AppSystem qaSystem,AppSystem testrailSystem,AppSystem releaseSystem,AppSystem productSystem) throws AppSystemException
    {
        super(name, layer);
        setDependency(DemoSystem.System_QA, qaSystem);
        setDependency(DemoSystem.System_Testrail, testrailSystem);
        setDependency(DemoSystem.System_RM, releaseSystem);
        setDependency(DemoSystem.System_PM, productSystem);
    }

}
