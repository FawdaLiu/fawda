package fawda.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class SimpleTypeConverter {
    private static final String NULL_STRING = "null";
    private static Map<Class<?>, Object> primitiveDefaults = new HashMap() {
    };

    public static Object convertValue(Object value, Class<?> toType) {
        Object result = null;

        if (value != null) {
            if ((value.getClass().isArray()) && (toType.isArray())) {
                Class componentType = toType.getComponentType();

                result = Array.newInstance(componentType, Array.getLength(value));

                int i = 0;
                for (int icount = Array.getLength(value); i < icount; i++) {
                    Array.set(result, i, convertValue(Array.get(value, i), componentType));
                }

            } else if ((toType == Integer.class) || (toType == Integer.TYPE)) {
                result = Integer.valueOf((int) longValue(value));
            } else if ((toType == Double.class) || (toType == Double.TYPE)) {
                result = new Double(doubleValue(value));
            } else if ((toType == Boolean.class) || (toType == Boolean.TYPE)) {
                result = booleanValue(value) ? Boolean.TRUE : Boolean.FALSE;
            } else if ((toType == Byte.class) || (toType == Byte.TYPE)) {
                result = Byte.valueOf((byte) (int) longValue(value));
            } else if ((toType == Character.class) || (toType == Character.TYPE)) {
                result = new Character((char) (int) longValue(value));
            } else if ((toType == Short.class) || (toType == Short.TYPE)) {
                result = Short.valueOf((short) (int) longValue(value));
            } else if ((toType == Long.class) || (toType == Long.TYPE)) {
                result = Long.valueOf(longValue(value));
            } else if ((toType == Float.class) || (toType == Float.TYPE)) {
                result = new Float(doubleValue(value));
            } else if (toType == BigInteger.class) {
                result = bigIntValue(value);
            } else if (toType == BigDecimal.class) {
                result = bigDecValue(value);
            } else if (toType == String.class) {
                result = stringValue(value);
            } else if (Enum.class.isAssignableFrom(toType)) {
                //result = enumValue(toType, value);
            } else if (Date.class == toType) {
                result = DF.parseCheck(value.toString());
            } else if (toType.isAssignableFrom(value.getClass())) {
                result = value;
            }
        } else if (toType.isPrimitive()) {
            result = primitiveDefaults.get(toType);
        }

        return result;
    }

    public static boolean booleanValue(Object value) {
        if (value == null) {
            return false;
        }
        Class c = value.getClass();
        if (c == Boolean.class)
            return ((Boolean) value).booleanValue();
        if (c == Character.class)
            return ((Character) value).charValue() != 0;
        if ((value instanceof Number))
            return ((Number) value).doubleValue() != 0.0D;
        if ((value instanceof String))
            return new Boolean((String) value).booleanValue();
        return true;
    }

    public static <T extends Enum<T>> Enum<T> enumValue(Class<T> toClass, Object o) {
        Enum result = null;
        if (o == null)
            result = null;
        else if ((o instanceof String[]))
            result = Enum.valueOf(toClass, ((String[]) (String[]) o)[0]);
        else if ((o instanceof String)) {
            result = Enum.valueOf(toClass, (String) o);
        }
        return result;
    }

    public static int intValue(Object value) throws NumberFormatException {
        if (value == null)
            return 0;
        Class c = value.getClass();
        if (c.getSuperclass() == Number.class)
            return ((Number) value).intValue();
        if (c == Boolean.class)
            return ((Boolean) value).booleanValue() ? 1 : 0;
        if (c == Character.class)
            return ((Character) value).charValue();
        return Integer.parseInt(stringValue(value, true));
    }

    public static long longValue(Object value) throws NumberFormatException {
        if (value == null)
            return 0L;
        Class c = value.getClass();
        if (c.getSuperclass() == Number.class)
            return ((Number) value).longValue();
        if (c == Boolean.class)
            return ((Boolean) value).booleanValue() ? 1L : 0L;
        if (c == Character.class)
            return ((Character) value).charValue();
        return Long.parseLong(stringValue(value, true));
    }

    public static double doubleValue(Object value) throws NumberFormatException {
        if (value == null)
            return 0.0D;
        Class c = value.getClass();
        if (c.getSuperclass() == Number.class)
            return ((Number) value).doubleValue();
        if (c == Boolean.class)
            return ((Boolean) value).booleanValue() ? 1.0D : 0.0D;
        if (c == Character.class)
            return ((Character) value).charValue();
        String s = stringValue(value, true);

        return s.length() == 0 ? 0.0D : Double.parseDouble(s);
    }

    public static BigInteger bigIntValue(Object value) throws NumberFormatException {
        if (value == null)
            return BigInteger.valueOf(0L);
        Class c = value.getClass();
        if (c == BigInteger.class)
            return (BigInteger) value;
        if (c == BigDecimal.class)
            return ((BigDecimal) value).toBigInteger();
        if (c.getSuperclass() == Number.class)
            return BigInteger.valueOf(((Number) value).longValue());
        if (c == Boolean.class)
            return BigInteger.valueOf(((Boolean) value).booleanValue() ? 1L : 0L);
        if (c == Character.class)
            return BigInteger.valueOf(((Character) value).charValue());
        return new BigInteger(stringValue(value, true));
    }

    public static BigDecimal bigDecValue(Object value) throws NumberFormatException {
        if (value == null)
            return BigDecimal.valueOf(0L);
        Class c = value.getClass();
        if (c == BigDecimal.class)
            return (BigDecimal) value;
        if (c == BigInteger.class)
            return new BigDecimal((BigInteger) value);
        if (c.getSuperclass() == Number.class)
            return new BigDecimal(((Number) value).doubleValue());
        if (c == Boolean.class)
            return BigDecimal.valueOf(((Boolean) value).booleanValue() ? 1L : 0L);
        if (c == Character.class)
            return BigDecimal.valueOf(((Character) value).charValue());
        return new BigDecimal(stringValue(value, true));
    }

    public static String stringValue(Object value, boolean trim) {
        String result;
        if (value == null) {
            result = "null";
        } else {
            result = value.toString();
            if (trim) {
                result = result.trim();
            }
        }
        return result;
    }

    public static String stringValue(Object value) {
        return stringValue(value, false);
    }
}