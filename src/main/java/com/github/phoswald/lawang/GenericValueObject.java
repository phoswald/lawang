package com.github.phoswald.lawang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.TreeMap;

class GenericValueObject implements InvocationHandler {

	private final Class<?> type;
	private final Map<String, Object> fields;

	private GenericValueObject(Class<?> type, Map<String, Object> fields) {
		this.type = type;
		this.fields = new TreeMap<>(fields);
	}

	static <T> T createProxy(Class<T> type, Map<String, Object> fields) {
		return type.cast(Proxy.newProxyInstance(
				type.getClassLoader(),
				new Class[] { type },
				new GenericValueObject(type, fields)));
	}

	@Override
	public String toString() {
		return type.getSimpleName() + " " + fields.toString();
	}

	@Override
	public boolean equals(Object other) {
		return type.isInstance(other) &&
				toString().equals(other.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public Object invoke(Object instance, Method method, Object[] args) throws Throwable {
		String name = method.getName();
		switch(name) {
			case "toString":
				return toString();
			case "equals":
				return equals(args[0]);
			case "hashCode":
				return hashCode();
			default:
				Object result = fields.get(method.getName());
				if(result == null && DefaultValues.requiresDefaultValue(method.getReturnType())) {
					result = DefaultValues.getDefaultValue(method.getReturnType());
				}
				return result;
		}
	}
}