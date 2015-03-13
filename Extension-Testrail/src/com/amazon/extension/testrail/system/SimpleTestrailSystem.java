package com.amazon.extension.testrail.system;

import com.amazon.infra.system.AppSystemException;
import com.amazon.infra.system.impl.SimpleAppSystemImpl;

public class SimpleTestrailSystem extends SimpleAppSystemImpl
{
    public SimpleTestrailSystem(String name, Layer layer) throws AppSystemException
    {
        super(name, layer);
    }
}
