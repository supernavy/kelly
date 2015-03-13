package com.amazon.infra.restapi.type;

import java.util.List;
import com.amazon.infra.restapi.DataType;

@SuppressWarnings("rawtypes")
public class ArrayDataType<L extends List, T> extends ListDataType<L>
{
    int min;
    int max;
    
    public ArrayDataType(Class<L> cls, DataType<T> itemType)
    {
        this(cls, itemType, 0, Integer.MAX_VALUE);
    }

    public ArrayDataType(Class<L> cls, DataType<T> itemType, int min, int max)
    {
        super(cls, new DataType<?>[] { itemType }, new DataType<?>[] { itemType });
        this.min = min;
        this.max = max;
        if (min > max) {
            throw new IllegalArgumentException(String.format("min[%d], max[%d] are not consistent", min, max));
        }
    }

    @Override
    public int getMinSize()
    {
        return min;
    }

    @Override
    public int getMaxSize()
    {
        return max;
    }

    @Override
    public DataType<?> getValueType(int i)
    {
        return super.getValueType(0);
    }
    
    @Override
    public String toString()
    {
        return new StringBuffer().append(super.toString()).append(",min=").append(min).append(",max=").append(max).toString();
    }
}
