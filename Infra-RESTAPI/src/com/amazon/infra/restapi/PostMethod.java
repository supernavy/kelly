package com.amazon.infra.restapi;

public class PostMethod<T> extends RESTfulMethod<T>
{
    boolean hasReturn;

    DataType<?> postDataType;

    public PostMethod(String basePath, int min, int max, DataType<?> postDataType)
    {
        super(basePath, min, max);
        this.postDataType = postDataType;
    }

    public T invoke(APIClient client, Object[] pathExtendValues, Object data) throws RESTfulMethodException
    {
        if (postDataType.instanceOf(data) && postDataType.validate(data)) {
            return super.invoke(Action.POST, client, pathExtendValues, null, data);
        }
        throw new IllegalArgumentException(String.format("post data value[%s] is invalid, expected type is [%s]", data, postDataType));
    }

    public DataType<?> getPostDataType()
    {
        return postDataType;
    }
}
