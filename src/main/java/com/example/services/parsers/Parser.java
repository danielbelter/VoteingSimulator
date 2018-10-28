package com.example.services.parsers;

@FunctionalInterface
public interface Parser<T> {
    T parse(String text);
}
