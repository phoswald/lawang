package com.github.phoswald.lawang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.TreeMap;

class GenericValueObject implements InvocationHandler {

	private final Map<String, Object> fields;

	private GenericValueObject(Map<String, Object> fields) {
		this.fields = new TreeMap<>(fields);
	}

	static <T> T createProxy(Class<T> type, Map<String, Object> fields) {
		return type.cast(Proxy.newProxyInstance(
				type.getClassLoader(), 
				new Class[] { type }, 
				new GenericValueObject(fields)));
	}

	@Override
	public Object invoke(Object instance, Method method, Object[] args) throws Throwable {
		String name = method.getName();
		switch(name) {
		case "toString":
			return fields.toString();
		case "hashCode":
			return fields.toString().hashCode();
		default:
			return fields.get(method.getName());
		}
	}
}