package com.amazon.infra.eventbus;

public interface EntityEvent<T> extends Event {
    T getEntityId();
}
