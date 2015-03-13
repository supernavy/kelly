package com.amazon.infra.commandbus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbsCommand<T> implements Command<T>
{
    Class<T> returnType;
    @SuppressWarnings("unchecked")
    public AbsCommand()
    {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t ;
        
        if(ParameterizedType.class.isInstance(p.getActualTypeArguments()[0]))
        {
            returnType = (Class<T>) ((ParameterizedType) p.getActualTypeArguments()[0]).getRawType();  
        } else 
        {
            returnType = (Class<T>) p.getActualTypeArguments()[0];
        }
    }
    
    public Class<T> getReturnType() {
        return returnType;
    }
}
