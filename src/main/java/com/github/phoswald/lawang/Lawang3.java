package com.github.phoswald.lawang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Lawang3 {

    private static final ThreadLocal<Context<?>> current = new ThreadLocal<>();

    public static <T> T create(Class<T> type, Consumer<T> initializer) {
        try(Context<T> context = new Context<>(type)) {
            initializer.accept(context.resolver.proxy);
            return GenericValueObject.createProxy(type, context.fields);
        }
    }

    public static <V> Assignment<V> set3(Supplier<V> getter) {
        return new Assignment<>(getter);
    }

    private static class Context <T> implements AutoCloseable {
        final Context<?> parent = current.get();
        final NameResolver<T> resolver;
        final Map<String, Object> fields = new HashMap<>();

        Context(Class<T> type) {
            this.resolver = new NameResolver<>(type);
            current.set(this);
        }

        @Override
        public void close() {
            current.set(parent);
        }
    }

    public static class Assignment<V> {
        private final Supplier<?> getter;

        Assignment(Supplier<V> getter) {
            this.getter = getter;
        }

        public void to(V value) {
            Context<?> context = current.get();
            context.fields.put(context.resolver.getNameOfGetter(getter), value);
        }
    }
}
