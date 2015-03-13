package com.amazon.extension.testrail.context;

import java.util.Map;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.restapi.GetMethod;
import com.amazon.infra.restapi.PostMethod;

public interface TestrailServiceContext
{
    <T> T sendGet(GetMethod<T> method, Object[] pathExtends, Map<String, Object> filters) throws AppContextException;
    <T> T sendPost(PostMethod<T> method, Object[] pathExtends, Object postData) throws AppContextException;
}
