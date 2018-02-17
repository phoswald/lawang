package com.github.phoswald.lawang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Lawang {

    @SafeVarargs
    public static <T> T create(Class<T> type, Pair<T>... pairs) {
        NameResolver<T> resolver = new NameResolver<>(type);
        Map<String, Object> fields = new HashMap<>();
        for(Pair<T> pair : pairs) {
            fields.put(resolver.getNameOfGetter(pair.getter), pair.value);
        }
        return GenericValueObject.createProxy(type, fields);
    }

    public static <T,V> PairLhs<T,V> set(Function<T, V> getter) {
        PairLhs<T,V> assignment = new PairLhs<>();
        assignment.getter = getter;
        return assignment;
    }

    public static class PairLhs<T,V> {
        Function<T,?> getter;

        public Pair<T> to(V value) {
            Pair<T> pair = new Pair<>();
            pair.getter = getter;
            pair.value = value;
            return pair;
        }
    }

    public static class Pair<T> {
        Function<T,?> getter;
        Object value;
    }
}
