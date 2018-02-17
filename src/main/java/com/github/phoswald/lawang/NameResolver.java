package com.github.phoswald.lawang;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

class NameResolver <T> {

	private final T proxy;
	private String lastName;

	NameResolver(Class<T> type) {
		this.proxy = type.cast(Proxy.newProxyInstance(
				type.getClassLoader(), new Class[] { type }, this::invoke));
	}

	String getNameOfGetter(Function<T, ?> getter) {
		getter.apply(proxy);
		return lastName;
	}

	private Object invoke(Object instance, Method method, Object[] args) {
		lastName = method.getName();
		return DefaultValues.getDefaultValue(method.getReturnType());
	}
}