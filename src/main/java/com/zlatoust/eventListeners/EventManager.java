package com.zlatoust.eventListeners;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventManager<T> {
    private final List<EventListener<T>> listeners = new ArrayList<>();

    public void registerListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    public void unregisterListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    public void notifyAdded(T t) {
        for (EventListener<T> listener : listeners) {
            listener.onAdded(t);
        }
    }

    public void notifyUpdated(T t) {
        for (EventListener<T> listener : listeners) {
            listener.onUpdated(t);
        }
    }

    public void notifyDeleted(T t) {
        for (EventListener<T> listener : listeners) {
            listener.onDeleted(t);
        }
    }
}