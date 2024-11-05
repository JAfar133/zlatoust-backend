package com.zlatoust.eventListeners;

public interface EventListener<T> {
    void onAdded(T event);
    void onUpdated(T event);
    void onDeleted(T event);
}

