package com.yuezy.monitor.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ClassWrapper {
	
	private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new ImmutableMap.Builder<Class<?>, Class<?>>()
			.put(boolean.class, Boolean.class).put(double.class, Double.class)
			.put(float.class, Float.class).put(int.class, Integer.class)
			.put(long.class, Long.class).put(short.class, Short.class).build();
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> wrap(Class<T> c) {
		return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
	}
}
