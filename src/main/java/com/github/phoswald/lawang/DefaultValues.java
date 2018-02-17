package com.github.phoswald.lawang;

class DefaultValues {

    static boolean requiresDefaultValue(Class<?> type) {
        return type.isPrimitive();
    }

    static Object getDefaultValue(Class<?> type) {
        if(type.isPrimitive()) {
            if(type == int.class) {
                return Integer.valueOf(0);
            }
        }
        return null;
    }
}
