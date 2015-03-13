package com.amazon.infra.restapi;

public class DataType<T> {   
    Class<T> cls;
    
    public DataType(Class<T> cls)
    {
        this.cls = cls;
    }
    
    public Class<T> getCls()
    {
        return cls;
    }
    
    public boolean instanceOf(Object obj) 
    {
        return this.cls.isInstance(obj);
    }
    
    public T cast(Object obj) 
    {
        return cls.cast(obj);
    }
    
    public boolean validate(Object obj)
    {
        return instanceOf(obj);
    }
    
    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("type=").append(getClass().getName()).append(", cls=").append(cls.getName());
        return sb.toString();
    }
}