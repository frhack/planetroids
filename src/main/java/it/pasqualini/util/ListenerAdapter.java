package it.pasqualini.util;

import java.util.ArrayList;
import java.util.List;

public class ListenerAdapter<T> {
    private final List<EventListener<T>> listeners = new ArrayList<>();

    public void addEventListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    public void removeEventListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    public void fire(T item) {
        listeners.forEach(listener -> listener.consume(item));
    }
}


