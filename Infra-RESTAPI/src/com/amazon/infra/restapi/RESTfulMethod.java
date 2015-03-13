package com.amazon.infra.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class RESTfulMethod<T>
{
    Logger logger = Logger.getLogger(getClass().getName());
    
    public enum Action
    {
        GET, POST
    };

    String basePath;
    int minPathExtend;
    int maxPathExtend;

    DataType<T> returnDataType;
   
    public RESTfulMethod(String basePath,int minPathExtend,int maxPathExtend)
    {
        this.basePath = basePath;
        this.minPathExtend = minPathExtend;
        this.maxPathExtend = maxPathExtend;
    }

    public void setReturnDataType(DataType<T> returnDataType)
    {
        this.returnDataType = returnDataType;
    }

    protected T invoke(Action action, APIClient client, Object[] pathExtendValues, Map<String, String> filters, Object data) throws RESTfulMethodException
    {
        String uri = generateUri(convertPathExtend(pathExtendValues), filters);
        try {
            switch (action) {
                case GET:
                    if (data != null) {
                        logger.warning(String.format("[Warning] data[%s] for GET is not null", data));
                    }
                    return returnResult(client.sendGet(uri));
                case POST:
                    if (data == null) {
                        logger.warning(String.format("[Warning] data[%s] for Post is null", data));
                    }
                    return returnResult(client.sendPost(uri, data));
                default:
                    throw new RuntimeException(String.format("Not supported Action[%s]", action));
            }
        } catch (Exception e) {
            logger.severe(String.format("uri[%s], pathExtendValues=[%s], filters=[%s], data=[%s]", uri, pathExtendValues, filters, data));
            throw new RESTfulMethodException(e);
        }
    }

    private String generateUri(List<String> pathExtendValues, Map<String, String> pathFilterValues)
    {
        StringBuffer sb = new StringBuffer(basePath);
        for (String s : pathExtendValues) {
            sb.append("/").append(s);
        }

        if (pathFilterValues != null) {
            for (Map.Entry<String, String> entry : pathFilterValues.entrySet()) {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        return sb.toString();
    }

    private List<String> convertPathExtend(Object[] pathExtendValues)
    {
        if(pathExtendValues==null && minPathExtend>0)
        {
            throw new IllegalArgumentException(String.format("min[%d], but input is null", minPathExtend));
        }
        if(pathExtendValues!=null)
        {
            if(pathExtendValues.length<minPathExtend || pathExtendValues.length>maxPathExtend)
            {
                throw new IllegalArgumentException(String.format("min[%d],max[%d], but input length is [%d]", minPathExtend, maxPathExtend, pathExtendValues.length));
            }
        }
        
        List<String> ret = new ArrayList<String>();
        if (pathExtendValues != null) {
            for (Object o : pathExtendValues) {
                ret.add(String.valueOf(o));
            }
        }

        return ret;
    }

    private T returnResult(Object ret)
    {
        if(returnDataType.validate(ret))
        {
            return returnDataType.cast(ret);
        }
        throw new RuntimeException(String.format("invalid return data[%s] for type[%s]", ret, returnDataType));
    }
}
