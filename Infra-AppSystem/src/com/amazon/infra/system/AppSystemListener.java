package com.amazon.infra.system;

public interface AppSystemListener
{
    public void onStart(AppSystem system);
    public void onShutdown(AppSystem system);
}
