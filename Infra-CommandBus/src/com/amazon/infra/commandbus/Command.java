package com.amazon.infra.commandbus;

public interface Command<T>
{
    public Class<T> getReturnType();
}
