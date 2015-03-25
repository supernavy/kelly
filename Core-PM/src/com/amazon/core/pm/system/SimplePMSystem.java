package com.amazon.core.pm.system;

import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimplePMSystem extends SimpleAppSystemImpl
{
    public SimplePMSystem(String name, Layer layer) throws AppSystemException
    {
        super(name, layer);
        getRepository(PMSystem.Repository_Product);
    }
}
