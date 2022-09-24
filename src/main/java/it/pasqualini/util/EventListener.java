package it.pasqualini.util;

public interface EventListener<T> {
    void consume(T item);

}