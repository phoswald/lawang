package com.github.phoswald.lawang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Lawang {

    public static <T> T create(Class<T> type) {
        return create(type, (b, it) -> b);
    }

    public static <T> T create(Class<T> type, BiFunction<Initializer<T>, T, Initializer<T>> initializerFunc) {
        var initializer = new Initializer<>(type);
        initializerFunc.apply(initializer, initializer.resolver.proxy);
        return GenericObject.createProxy(type, initializer.fields);
    }

    public static <T> T create(Class<T> type, Map<String, Object> map) {
        return GenericObject.createProxy(type, map);
    }

    public static Map<String, Object> toMap(Object instance) {
        return GenericObject.getFields(instance);
    }

    public static class Initializer<T> {
        private final NameResolver<T> resolver;
        private final Map<String, Object> fields = new HashMap<>();

        private Initializer(Class<T> type) {
            this.resolver = new NameResolver<>(type);
        }

        public <V> Assignment<T,V> set(Supplier<V> getter) {
            return new Assignment<>(this, getter);
        }
    }

    public static class Assignment<T,V> {
        private final Initializer<T> initializer;
        private final Supplier<?> getter;

        private Assignment(Initializer<T> initializer, Supplier<V> getter) {
            this.initializer = initializer;
            this.getter = getter;
        }

        public Initializer<T> to(V value) {
            initializer.fields.put(initializer.resolver.getNameOfGetter(getter), value);
            return initializer;
        }
    }
}
