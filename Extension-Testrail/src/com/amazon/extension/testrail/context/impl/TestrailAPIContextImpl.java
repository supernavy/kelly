package com.amazon.extension.testrail.context.impl;

import java.util.Map;
import com.amazon.extension.testrail.api.TestrailException;
import com.amazon.extension.testrail.context.TestrailAPIContext;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.restapi.APIClient;
import com.amazon.infra.restapi.GetMethod;
import com.amazon.infra.restapi.PostMethod;
import com.amazon.infra.restapi.RESTfulMethodException;

public class TestrailAPIContextImpl implements TestrailAPIContext
{
    APIClient client;
    
    public TestrailAPIContextImpl(APIClient client) throws TestrailException
    {
        this.client = client;
    }

    @Override
    public <T> T sendGet(GetMethod<T> method, Object[] pathExtends, Map<String, Object> filters) throws AppContextException
    {
        try {
            T ret = method.invoke(client, pathExtends, filters);
            return ret;
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
