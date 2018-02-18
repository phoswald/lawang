package com.github.phoswald.lawang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Lawang4 {

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

        public <V> Assignment<V> set(Supplier<V> getter) {
            return new Assignment<>(getter);
        }

        public class Assignment<V> {
            private final Supplier<?> getter;

            Assignment(Supplier<V> getter) {
                this.getter = getter;
            }

            public Initializer<T> to(V value) {
                fields.put(resolver.getNameOfGetter(getter), value);
                return Initializer.this;
            }
        }
    }
}
