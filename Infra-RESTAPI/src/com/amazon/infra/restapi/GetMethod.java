package com.amazon.infra.restapi;

import java.util.HashMap;
import java.util.Map;

public class GetMethod<T> extends RESTfulMethod<T>
{
    Map<String, DataType<?>> requestFilters = new HashMap<String, DataType<?>>();

    public GetMethod(String basePath, int min, int max)
    {
        super(basePath, min, max);
    }

    public void addRequestFilter(String key, DataType<?> type)
    {
        this.requestFilters.put(key, type);
    }

    public T invoke(APIClient client, Object[] pathExtendValues, Map<String, Object> requestFilterValues) throws RESTfulMethodException
    {

        return super.invoke(Action.GET, client, pathExtendValues, convertRequestFilters(requestFilterValues), null);
    }

    private Map<String, String> convertRequestFilters(Map<String, Object> requestFilterValues)
    {
        Map<String, String> ret = new HashMap<String, String>();
        if(requestFilterValues !=null)
        {
            for (Map.Entry<String, Object> entry : requestFilterValues.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (!requestFilters.containsKey(key)) {
                    throw new IllegalArgumentException(String.format("Not supported key[%s] in keys[%s]", key, requestFilters.keySet()));
                }
                if (!requestFilters.get(key).instanceOf(value)) {
                    throw new IllegalArgumentException(String.format("Not supported value[%s] for type[%s]", value, requestFilters.get(key)));
                }
                ret.put(key, String.valueOf(value));
            }
        }

        return ret;
    }
}
