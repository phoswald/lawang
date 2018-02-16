package com.github.phoswald.lawang;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Lawang {

	public static <T> T build(Class<T> type) {
		return builder(type).build();
	}

	public static <T> Builder<T> builder(Class<T> type) {
		return new Builder<T>(type);
	}

	public static class Builder<T> {

		private final Class<T> type;
		private final NameResolver<T> resolver;
		private final Map<String, Object> fields = new HashMap<>();

		Builder(Class<T> type) {
			this.type = Objects.requireNonNull(type);
			this.resolver = new NameResolver<>(type);
		}

		<V> Builder<T> with(Function<T, V> getter, V value) {
			fields.put(resolver.getNameOfGetter(getter), value);
			return this;
		}

		public T build() {
			return GenericValueObject.createProxy(type, fields);
		}
	}
}
