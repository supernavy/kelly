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

        returnType = guessReturnType(p.getActualTypeArguments()[1]);
        
    }
    
    @SuppressWarnings("unchecked")
    private Class<R> guessReturnType(Type type)
    {
        if(ParameterizedType.class.isInstance(type))
        {
            ParameterizedType pType = (ParameterizedType) type ;
            return guessReturnType(pType.getActualTypeArguments()[0]);
        } else
        {
            return (Class<R>)type;
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
