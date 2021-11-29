package ru.softdarom.qrcheck.events.mapper;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Checker {

    default <T> void whenNotNull(T object, Consumer<T> consumer) {
        whenNotNull(object, consumer, null);
    }

    default <T> void whenNotNull(T object, Consumer<T> consumer, Action nullAction) {
        when(object, Objects::nonNull, consumer, nullAction);
    }

    default <T> void whenNot(T object, Predicate<T> predicate, Consumer<T> consumer) {
        when(object, predicate.negate(), consumer, null);
    }

    default <T> void when(T object, Predicate<T> predicate, Consumer<T> consumer) {
        when(object, predicate, consumer, null);
    }

    default <T> void when(T object, Predicate<T> predicate, Consumer<T> consumer, Action nullAction) {
        if (predicate.test(object)) {
            consumer.accept(object);
        } else {
            if (nullAction != null) {
                nullAction.execute();
            }
        }
    }

    @FunctionalInterface
    interface Action {
        void execute();
    }

}