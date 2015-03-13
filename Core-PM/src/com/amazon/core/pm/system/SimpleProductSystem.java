package com.amazon.core.pm.system;

import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleProductSystem extends SimpleAppSystemImpl
{
    public SimpleProductSystem(String name, Layer layer) throws AppSystemException
    {
        super(name, layer);
        getRepository(ProductSystem.Repository_Product);
    }
}
