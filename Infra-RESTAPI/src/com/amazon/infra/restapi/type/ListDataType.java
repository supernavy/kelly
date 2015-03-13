package com.amazon.infra.restapi.type;

import java.util.Arrays;
import java.util.List;
import com.amazon.infra.restapi.DataType;

@SuppressWarnings({"rawtypes","unchecked"})
public class ListDataType<X extends List> extends DataType<X>
{
    public static ListDataType<List> EMPTY = new ListDataType(List.class, new DataType<?>[]{}, new DataType<?>[]{});
    
    DataType<?>[] minimalValueTypes;
    DataType<?>[] extraValueTypes;
    
    public ListDataType(Class<X> cls, DataType<?>[] minimalValueTypes, DataType<?>[] extraValueTypes)
    {
        super(cls);
        this.minimalValueTypes = minimalValueTypes;
        this.extraValueTypes = extraValueTypes;
    }
    
    public int getMinSize() {
        return minimalValueTypes.length;
    }
    
    public int getMaxSize() {
        return getMinSize() + extraValueTypes.length;
    }
    
    public int getSize(Object instance) {
        return super.cast(instance).size();
    }

    public DataType<?> getValueType(int i)
    {
        if(i<getMinSize())
        {
            return minimalValueTypes[i];
        }
        else
        {
            return extraValueTypes[i-getMinSize()];
        }
    }
    
    public Object getValue(Object instance, int i)
    {
        return getValueType(i).cast(cast(instance).get(i));
    }
    
    public boolean validate(Object obj)
    {
        if (! super.validate(obj)) {
            System.out.println("asdasdasd");
            return false;
        }
        int length = cast(obj).size();
        if(length>getMaxSize() || length<getMinSize())
        {
            System.out.println(String.format("min[%d], max[%d], actual[%d]", getMinSize(), getMaxSize(), length));
            return false;
        }
        
        List l = cast(obj);
        for(int i = 0; i< l.size(); i++)
        {
            if(!getValueType(i).validate(l.get(i)))
            {
                System.out.println("xxxxxxxxxxxxxxx");
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString()
    {
        return new StringBuffer().append(super.toString()).append(",minimalValueTypes=").append(Arrays.toString(minimalValueTypes)).append(",extraValueTypes=").append(Arrays.toString(extraValueTypes)).toString();
    }
}
