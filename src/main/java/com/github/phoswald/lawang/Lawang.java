package com.github.phoswald.lawang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Lawang {

    public static <T> T create(Class<T> type) {
        return create(type, (cx, it) -> cx);
    }

    public static <T> T create(Class<T> type, BiFunction<Initializer<T>, T, Initializer<T>> initializerFunc) {
        Initializer<T> initializer = new Initializer<>(type);
        initializerFunc.apply(initializer, initializer.resolver.proxy);
        return GenericValueObject.createProxy(type, initializer.fields);
    }

    public static class Initializer<T> {
        final NameResolver<T> resolver;
        final Map<String, Object> fields = new HashMap<>();

        Initializer(Class<T> type) {
            this.resolver = new NameResolver<>(type);
        }

        public <V> Assignment<T,V> set(Supplier<V> getter) {
            return new Assignment<>(this, getter);
        }
    }

    public static class Assignment<T,V> {
        final Initializer<T> initializer;
        final Supplier<?> getter;

        Assignment(Initializer<T> initializer, Supplier<V> getter) {
            this.initializer = initializer;
            this.getter = getter;
        }

        public Initializer<T> to(V value) {
            initializer.fields.put(initializer.resolver.getNameOfGetter(getter), value);
            return initializer;
        }
    }
}
