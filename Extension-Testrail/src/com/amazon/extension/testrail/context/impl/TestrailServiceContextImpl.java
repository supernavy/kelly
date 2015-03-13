package com.amazon.extension.testrail.context.impl;

import java.util.Map;
import com.amazon.extension.testrail.api.TestrailException;
import com.amazon.extension.testrail.context.TestrailServiceContext;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.restapi.APIClient;
import com.amazon.infra.restapi.GetMethod;
import com.amazon.infra.restapi.PostMethod;
import com.amazon.infra.restapi.RESTfulMethodException;

public class TestrailServiceContextImpl extends AbsAppContextImpl implements TestrailServiceContext
{
    APIClient client;
    
    public TestrailServiceContextImpl(EventBus eventBus, APIClient client) throws TestrailException
    {
        super(eventBus);
        this.client = client;
    }

    @Override
    public <T> T sendGet(GetMethod<T> method, Object[] pathExtends, Map<String, Object> filters) throws AppContextException
    {
        try {
            return method.invoke(client, pathExtends, filters);
        } catch (RESTfulMethodException e) {
            throw new AppContextException(e);
        }
    }

    @Override
    public <T> T sendPost(PostMethod<T> method, Object[] pathExtends, Object postData) throws AppContextException
    {
        try {
            return method.invoke(client, pathExtends, postData);
        } catch (RESTfulMethodException e) {
            throw new AppContextException(e);
        }
    }

    
}
