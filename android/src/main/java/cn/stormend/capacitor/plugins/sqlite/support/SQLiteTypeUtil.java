package cn.stormend.capacitor.plugins.sqlite.support;

public final class SQLiteTypeUtil {

    private SQLiteTypeUtil() {
    }

    private static final Class<?>[] PRIMITIVE_TYPES = {int.class, long.class, short.class,
            float.class, double.class, byte.class, boolean.class, char.class, Integer.class, Long.class,
            Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};


    /**
     * is primitive or not
     *
     * @param type
     * @return
     */
    private boolean isPrimitiveOrString(Class<?> type) {
        if (String.class.equals(type)) {
            return true;
        }
        for (Class<?> standardPrimitive : PRIMITIVE_TYPES) {
            if (standardPrimitive.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean isString(Class<?> type) {
        return String.class.equals(type);
    }

    private boolean isInteger(Class<?> type) {
        return int.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type);
    }

    private boolean isFloat(Class<?> type) {
        return float.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type);
    }

    private boolean isDouble(Class<?> type) {
        return double.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type);
    }

    private boolean isLong(Class<?> type) {
        return long.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type);
    }

    private boolean isBoolean(Class<?> type) {
        return boolean.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type);
    }


}
