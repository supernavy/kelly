package com.amazon.infra.commandbus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbsCommandHandler<T extends Command<R>, R> implements CommandHandler<T,R>
{
    Class<T> commandType;
    Class<R> returnType;

    @SuppressWarnings("unchecked")
    public AbsCommandHandler()
    {
        Type t = getClass().getGenericSuperclass();  
        ParameterizedType p = (ParameterizedType) t ;    
        commandType = (Class<T>) p.getActualTypeArguments()[0];

        if(ParameterizedType.class.isInstance(p.getActualTypeArguments()[1]))
        {
            ParameterizedType p1 = (ParameterizedType) p.getActualTypeArguments()[1] ;
            returnType = (Class<R>) p1.getActualTypeArguments()[0];  
        } else
        {
            returnType = (Class<R>) p.getActualTypeArguments()[1];
        }
        
    }

    @Override
    public Class<T> getCommandType()
    {
        return commandType;
    }
    
    @Override
    public Class<R> getReturnType()
    {
        return returnType;
    }
}
