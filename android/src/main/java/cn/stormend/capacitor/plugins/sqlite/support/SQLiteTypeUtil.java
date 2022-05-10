package cn.stormend.capacitor.plugins.sqlite.support;

public final class SQLiteTypeUtil {

    private SQLiteTypeUtil() {
    }

    private static final Class<?>[] PRIMITIVE_TYPES = {int.class, long.class, short.class,
            float.class, double.class, byte.class, boolean.class, char.class, Integer.class, Long.class,
            Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};


    public static final int TYPE_STRING = 0;
    public static final int TYPE_INT = 1;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_DOUBLE = 3;
    public static final int TYPE_LONG = 4;
    public static final int TYPE_BOOL = 5;
    public static final int TYPE_BYTE = 6;
    public static final int TYPE_UNKNOWN = 6;


    public static int determineType(Class<?> type) {
        if (isPrimitiveOrString(type)) {
            if (isBoolean(type)) {
                return TYPE_BOOL;
            } else if (isDouble(type)) {
                return TYPE_DOUBLE;
            } else if (isFloat(type)) {
                return TYPE_FLOAT;
            } else if (isInteger(type)) {
                return TYPE_INT;
            } else if (isLong(type)) {
                return TYPE_LONG;
            } else if (isByte(type)) {
                return TYPE_BYTE;
            } else {
                return TYPE_STRING;
            }
        } else {
            return TYPE_UNKNOWN;
        }
    }


    /**
     * is primitive or not
     *
     * @param type
     * @return
     */
    private static boolean isPrimitiveOrString(Class<?> type) {
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

    private static boolean isString(Class<?> type) {
        return String.class.equals(type);
    }

    private static boolean isInteger(Class<?> type) {
        return int.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type);
    }

    private static boolean isFloat(Class<?> type) {
        return float.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type);
    }

    private static boolean isDouble(Class<?> type) {
        return double.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type);
    }

    private static boolean isLong(Class<?> type) {
        return long.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type);
    }

    private static boolean isBoolean(Class<?> type) {
        return boolean.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type);
    }

    private static boolean isByte(Class<?> type) {
        return byte.class.isAssignableFrom(type) || Byte.class.isAssignableFrom(type);
    }
}
