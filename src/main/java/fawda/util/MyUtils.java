package fawda.util;

import com.risen.hp.reflection.MetaObject;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.junit.Assert;
import org.springframework.util.*;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.lang.reflect.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

// Referenced classes of package com.risen.base.util:
//            CmmTokenParser, SimpleTypeConverter, DF

public abstract class MyUtils {
    public static final Pattern PROPERTY = Pattern.compile("([a-z])([A-Z])");

    public static final Pattern COLUMN = Pattern.compile("_([a-zA-Z])");

    public static final Pattern CHECK = Pattern.compile("[0-9A-Za-z]{2,64}?");

    public static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([\\w\\.\\[\\]]+)\\}");

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap(8);
    public static final String NOW = "NOW";
    public static final String TIME = "TIME";
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String YM = "YM";
    public static final String DAY = "DAY";
    public static final String HMS = "HMS";
    public static final String YMS = "YMS";
    public static final String YMSSSS = "YMSSSS";

    public static Class<?> getPrimitiveWrapper(Class<?> key) {
        return (Class) primitiveWrapperTypeMap.get(key);
    }

    public static <T> T getValue(T value, T dv) {
        return value == null ? dv : value;
    }

    public static <T> T getValue(String value, T dv) {
        if (StringUtils.hasText(value)) {
            Class toClass = dv == null ? Object.class : dv.getClass();
            return (T) SimpleTypeConverter.convertValue(value, toClass);
        }
        return dv;
    }

    public static String getSuffix(String value) {
        return getSuffix(value, "_");
    }

    public static String getSuffix(String value, String join) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        if (!value.startsWith(join)) {
            value = join + value;
        }
        return value;
    }

    public static <T extends Enum<T>> boolean isEnum(Class<T> clazz, String name) {
        try {
            Enum.valueOf(clazz, name);
            return true;
        } catch (Throwable e) {
        }
        return false;
    }

    public static <T> T getProperty(Object root, String property) {
        return null;
    }

    public static String[] addFieldsIfNotExist(String[] root, String[] fields) {
        Set fieldSet = new LinkedHashSet(Arrays.asList(root));
        for (String field : fields) {
            if (!fieldSet.contains(field)) {
                fieldSet.add(field);
            }
        }
        return (String[]) fieldSet.toArray(new String[0]);
    }

    public static char nextChar62(char x) {
        if (x == '9')
            x = 'A';
        else if (x == 'Z')
            x = 'a';
        else if (x == 'z')
            x = '0';
        else {
            x = (char) (x + '\001');
        }
        return x;
    }

    public static char nextChar36(char x) {
        if (x == '9')
            x = 'A';
        else if (x >= 'Z')
            x = '0';
        else {
            x = (char) (x + '\001');
        }
        return x;
    }

    public static String getNextLevelCode(String currentCode) {
        return getNextLevelCode(currentCode, 2);
    }

    public static String getNextLevelCode(String currentCode, int step) {
        Assert.assertNotNull(currentCode, "当前代码不可以为空");
        int currentCodeLength = currentCode.length();
        if ((CHECK.matcher(currentCode).matches()) && (currentCodeLength % step == 0)) {
            StringBuilder fixedPart = new StringBuilder(currentCode.substring(0, currentCodeLength - step));
            StringBuilder changePart = new StringBuilder(currentCode.substring(currentCodeLength - step));
            boolean haveCarry = true;
            for (int i = changePart.length() - 1; (i >= 0) && (haveCarry); i--) {
                char c = nextChar36(changePart.charAt(i));
                if (c != '0') {
                    haveCarry = false;
                }
                changePart.setCharAt(i, c);
            }
            return changePart.toString();
        }
        throw new IllegalArgumentException(currentCode + ":非法编码");
    }

    public static <T> T buildObject(Class<T> clazz) {
        Assert.assertNotNull("需要实例化的类对象不能为NULL", clazz);
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("实例化类对象异常", e);
        }
    }

    public static <T> T buildObject(Class<T> clazz, Object[] args) {
        Assert.assertNotNull("需要实例化的类对象不能为NULL", clazz);
        try {
            return ConstructorUtils.invokeConstructor(clazz, args);
        } catch (Exception e) {
            throw new RuntimeException("实例化类对象异常", e);
        }
    }

    public static Class<?> forName(String className) {
        try {
            return ClassUtils.forName(className, MyUtils.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (LinkageError e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object target, String field, Object value) {
        Field fieldObject = ReflectionUtils.findField(target.getClass(), field);
        ReflectionUtils.makeAccessible(fieldObject);
        ReflectionUtils.setField(fieldObject, target, value);
    }

    public static Object getFieldValue(Object target, String field) {
        Field fieldObject = ReflectionUtils.findField(target.getClass(), field);
        ReflectionUtils.makeAccessible(fieldObject);
        return ReflectionUtils.getField(fieldObject, target);
    }

    public static List<String> arrayToList(String[] seq) {
        List list = new ArrayList(seq.length);
        for (String s : seq) {
            list.add(s);
        }
        return list;
    }

    public static String formEncoded(Map<String, Object> argMap) {
        if (CollectionUtils.isEmpty(argMap)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator it = argMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            String value = getValue(entry.getValue(), "").toString();
            try {
                String unit = URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
                sb.append(unit);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (it.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    public static String removeKeys(String key, Collection<String> remove) {
        if (!StringUtils.hasText(key)) {
            return key;
        }

        List uuids = CC(key);
        uuids.removeAll(remove);
        return StringUtils.collectionToCommaDelimitedString(uuids);
    }

    public static Map<String, String> entityToOwnerText(Map<String, String> ownerText, Map<String, List<String>> entityToOwner) {
        Iterator it = entityToOwner.entrySet().iterator();
        Map<String, String> entityToOwnerText = new HashMap<String, String>();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String entityUuid = (String) entry.getKey();
            List<String> ownerUuids = (List<String>) entry.getValue();
            List<String> texts = new LinkedList<String>();
            for (String ownerUuid : ownerUuids) {
                texts.add(ownerText.get(ownerUuid));
            }
            entityToOwnerText.put(entityUuid, StringUtils.collectionToCommaDelimitedString(texts));
        }
        return entityToOwnerText;
    }

    public static List<String> CC(String seq, boolean set) {
        String[] strArray = StringUtils.commaDelimitedListToStringArray(seq);
        Collection result = set ? new LinkedHashSet(strArray.length) : new ArrayList(strArray.length);
        for (String str : strArray) {
            str = StringUtils.trimWhitespace(str);
            if (StringUtils.hasText(str)) {
                result.add(str);
            }
        }
        return (List) (set ? new ArrayList(result) : result);
    }

    public static List<String> CC(String seq) {
        return CC(seq, false);
    }

    public static String filterRepeat(Object[] values) {
        return StringUtils.collectionToCommaDelimitedString(filterRepeatToSet(values));
    }

    public static Set<String> filterRepeatToSet(Object[] values) {
        List<String> strValue = new LinkedList<String>();
        for (Object object : values) {
            if (object != null) {
                if (ObjectUtils.isArray(object))
                    strValue.addAll(filterRepeatToSet(new Object[] { object }));
                else if ((object instanceof Collection)) {
                    strValue.addAll(filterRepeatToSet(((Collection) object).toArray(new Object[0])));
                } else if (object.toString().indexOf(",") >= 0)
                    strValue.addAll(CC(object.toString()));
                else {
                    strValue.add(object.toString());
                }
            }
        }
        Set<String> result = new LinkedHashSet<String>(strValue.size());
        for (String str : strValue) {
            str = StringUtils.trimWhitespace(str);
            if (StringUtils.hasText(str)) {
                result.add(str);
            }
        }
        return result;
    }

    public static <T> Class<T> getGenericClass(Class<?> genericClass) {
        return getGenericClass(genericClass, 0);
    }

    public static <T> Class<T> getGenericClass(Class<?> genericClass, int index) {
        Class modelClass = null;
        TypeVariable[] tv = genericClass.getTypeParameters();
        if (tv.length == 0) {
            Type gsc = genericClass.getGenericSuperclass();
            if ((gsc instanceof ParameterizedType)) {
                ParameterizedType pt = (ParameterizedType) gsc;
                modelClass = (Class) pt.getActualTypeArguments()[index];
            } else {
                modelClass = getGenericClass((Class) gsc);
            }
        } else {
            modelClass = getGenericClass(genericClass, index + "");
        }
        return modelClass;
    }

    public static <T> Class<T> getGenericClass(Class<?> genericClass, String name) {
        Class modelClass = (Class) parseGenericClass(genericClass).get(name);
        if (modelClass == null) {
            throw new RuntimeException("无法获取指定的泛型类相应的类型：" + name);
        }
        return modelClass;
    }

    public static Map<String, Class<?>> parseGenericClass(Class<?> genericClass) {
        TypeVariable[] tv = genericClass.getTypeParameters();
        Map map = new HashMap();
        int idx = 0;
        for (TypeVariable typeVariable : tv) {
            Type type = typeVariable.getBounds()[0];
            if ((type instanceof Class)) {
                map.put(typeVariable.getName(), (Class) type);
                map.put(idx + "", (Class) type);
            } else if ((type instanceof ParameterizedType)) {
                Type rawType = ((ParameterizedType) type).getRawType();
                map.put(typeVariable.getName(), (Class) rawType);
                map.put(idx + "", (Class) rawType);
            } else {
                throw new RuntimeException("不支持的泛型反射类:" + type);
            }
            idx++;
        }
        itr(genericClass, map, idx);
        return map;
    }

    public static <T> Class<T> getGenericMethodReturnType(Class<?> clazz, String name, int index) {
        Method method = ReflectionUtils.findMethod(clazz, name);
        Type type = method.getGenericReturnType();
        if ((type instanceof ParameterizedType)) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return (Class) types[index];
        }
        throw new RuntimeException("必须指定一个一级泛型方法");
    }

    public static <T> Class<T> getGenericMethodReturnType(Class<?> clazz, String name) {
        return getGenericMethodReturnType(clazz, name, 0);
    }

    private static void itr(Class<?> genericClass, Map<String, Class<?>> map, int idx) {
        Class superclass = genericClass.getSuperclass();
        if (Object.class.equals(superclass)) {
            return;
        }

        TypeVariable[] tv = superclass.getTypeParameters();
        Type[] actualTypes = null;
        if (tv.length > 0) {
            Type gsc = genericClass.getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) gsc;
            actualTypes = pt.getActualTypeArguments();
        }
        for (int i = 0; i < tv.length; i++) {
            if (!map.containsKey(tv[i].getName())) {
                if ((actualTypes[i] instanceof Class))
                    map.put(tv[i].getName(), (Class) actualTypes[i]);
                else if ((actualTypes[i] instanceof TypeVariable)) {
                    map.put(tv[i].getName(), (Class) ((TypeVariable) actualTypes[i]).getBounds()[0]);
                }

                map.put("" + idx++, map.get(tv[i].getName()));
            }
        }
        itr(superclass, map, idx);
    }

    public static String mapperColName(String propName) {
        return PROPERTY.matcher(propName).replaceAll("$1_$2").toUpperCase();
    }

    public static String mapperPropName(String colName) {
        return mapperPropName(colName, false);
    }

    public static String mapperPropName(String colName, boolean first) {
        colName = colName.toLowerCase();
        String[] names = colName.split("_");
        StringBuilder buffer = new StringBuilder(colName.length());
        for (int i = 0; i < names.length; i++) {
            String frag = (i == 0) && (!first) ? names[i] : StringUtil.capitalize(names[i]);
            buffer.append(frag);
        }
        return buffer.toString();
    }

    public static WebApplicationContext getAppCtx(Map<String, Object> app) {
        String appCtxKey = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        return (WebApplicationContext) app.get(appCtxKey);
    }

    public static String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        while (whitespaceStripper.hasMoreTokens()) {
            builder.append(whitespaceStripper.nextToken());
            builder.append(" ");
        }
        return builder.toString();
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>[] paramTypes) {
        Assert.assertNotNull("Class must not be null", clazz);
        Assert.assertNotNull("Method name must not be null", name);
        Class searchType = clazz;
        while (searchType != null) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods();
            for (Method method : methods) {
                if ((name.equals(method.getName())) && ((paramTypes == null) || (paramTypesCompatible(paramTypes, method.getParameterTypes())))) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private static boolean paramTypesCompatible(Class<?>[] tests, Class<?>[] candidates) {
        if (tests.length != candidates.length) {
            return false;
        }
        for (int i = 0; i < candidates.length; i++) {
            Class candidate = candidates[i];
            Class test = tests[i];
            if (!test.isAssignableFrom(candidate)) {
                return false;
            }
        }
        return true;
    }

    public static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
        Method specificMethod = null;
        if ((method != null) && (isOverridable(method, targetClass)) && (targetClass != null) && (!targetClass.equals(method.getDeclaringClass()))) {
            specificMethod = findMethod(targetClass, method.getName(), method.getParameterTypes());
        }
        return specificMethod != null ? specificMethod : method;
    }

    private static boolean isOverridable(Method method, Class<?> targetClass) {
        if (Modifier.isPrivate(method.getModifiers())) {
            return false;
        }
        if ((Modifier.isPublic(method.getModifiers())) || (Modifier.isProtected(method.getModifiers()))) {
            return true;
        }
        return ClassUtils.getPackageName(method.getDeclaringClass()).equals(ClassUtils.getPackageName(targetClass));
    }

    public static Method findMatchingMethod(Class<?> serviceClass, String targetMethod, Object[] arguments) {
        int argCount = arguments.length;

        Method[] candidates = ReflectionUtils.getAllDeclaredMethods(serviceClass);
        int minTypeDiffWeight = 2147483647;
        Method matchingMethod = null;

        for (Method candidate : candidates) {
            if (candidate.getName().equals(targetMethod)) {
                Class[] paramTypes = candidate.getParameterTypes();
                if (paramTypes.length == argCount) {
                    int typeDiffWeight = getTypeDifferenceWeight(paramTypes, arguments);
                    if (typeDiffWeight < minTypeDiffWeight) {
                        minTypeDiffWeight = typeDiffWeight;
                        matchingMethod = candidate;
                    }
                }
            }
        }
        return matchingMethod;
    }

    public static int getTypeDifferenceWeight(Class<?>[] paramTypes, Object[] args) {
        int result = 0;
        for (int i = 0; i < paramTypes.length; i++) {
            if (!ClassUtils.isAssignableValue(paramTypes[i], args[i])) {
                return 2147483647;
            }
            if (args[i] != null) {
                Class paramType = paramTypes[i];
                Class superClass = args[i].getClass().getSuperclass();
                while (superClass != null) {
                    if (paramType.equals(superClass)) {
                        result += 2;
                        superClass = null;
                    } else if (ClassUtils.isAssignable(paramType, superClass)) {
                        result += 2;
                        superClass = superClass.getSuperclass();
                    } else {
                        superClass = null;
                    }
                }
                if (paramType.isInterface()) {
                    result += 1;
                }
            }
        }
        return result;
    }

    public static Method getMethodByName(Class<?> serviceClass, String methodName) {
        Class parent = serviceClass;
        while (!Object.class.equals(parent)) {
            Method[] methods = serviceClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
            parent = parent.getSuperclass();
        }
        return null;
    }

    public static <T> T deepClone(T source) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(source);
            oos.close();
            bos.close();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Serializable result = (Serializable) ois.readObject();
            bis.close();
            ois.close();
            return (T) result;
        } catch (Exception e) {
            throw new RuntimeException("对像深拷贝失败", e);
        } finally {
        }
    }

    public static boolean seqEquals(String older, String newer) {
        older = (String) getValue(older, "");
        newer = (String) getValue(newer, "");
        if ((older.indexOf(",") < 0) || (newer.indexOf(",") < 0)) {
            return older.equals(newer);
        }
        List oldList = CC(older);
        List newList = CC(newer);
        return !parserDiffer(oldList, newList).hasChange();
    }

    public static <T> OCR<T> parserDiffer(Collection<T> older, Collection<T> newer) {
        return parserDiffer(older, newer, false);
    }

    public static <T> OCR<T> parserDiffer(Collection<T> older, Collection<T> newer, boolean update) {
        OCR result = new OCR();
        Set olderSet = new LinkedHashSet(older);
        Set newerSet = new LinkedHashSet(newer);
        for (Iterator localIterator = newerSet.iterator(); localIterator.hasNext();) {
            Object t = localIterator.next();
            if (olderSet.contains(t)) {
                olderSet.remove(t);
                if (update)
                    result.getUpdate().add(t);
            } else {
                result.getAdd().add(t);
            }
        }
        if (!CollectionUtils.isEmpty(olderSet)) {
            result.setDelete(new ArrayList(olderSet));
        }
        return result;
    }

    public static void buildTimeCtx(Date date, Map<String, Object> timeCtx) {
        timeCtx.put("NOW", date);
        timeCtx.put("TIME", date.getTime() + "");
        timeCtx.put("YEAR", DF.yyyy.format(date));
        timeCtx.put("MONTH", DF.MM.format(date));
        timeCtx.put("YM", DF.yyyyMM.format(date));
        timeCtx.put("YMS", DF.FS.format(date));
        timeCtx.put("HMS", DF.HMS.format(date));
        timeCtx.put("DAY", DF.dd.format(date));
        timeCtx.put("YMSSSS", DF.FMS.format(date));
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if ((hexString == null) || (hexString.equals(""))) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = ((byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)])));
        }
        return d;
    }

    public static <K, V> Map<K, V> CollectionToMap(Collection<?> collection, String prop) {
        Map map = new LinkedHashMap(collection.size() * 4 / 3 + 4);
        String vp = "";
        if ((StringUtils.hasText(prop)) && (prop.indexOf(",") > 0)) {
            vp = prop.substring(prop.indexOf(",") + 1);
            prop = prop.substring(0, prop.indexOf(","));
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            MetaObject metaObject = MetaObject.forObject(value);
            Object key = metaObject.getValue(prop);
            if (StringUtils.hasText(vp)) {
                value = metaObject.getValue(vp);
            }
            map.put(key, value);
        }
        return map;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    static {
        primitiveWrapperTypeMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperTypeMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperTypeMap.put(Character.TYPE, Character.class);
        primitiveWrapperTypeMap.put(Double.TYPE, Double.class);
        primitiveWrapperTypeMap.put(Float.TYPE, Float.class);
        primitiveWrapperTypeMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperTypeMap.put(Long.TYPE, Long.class);
        primitiveWrapperTypeMap.put(Short.TYPE, Short.class);
    }

    public static class OCR<T> {
        List<T> add;
        List<T> delete;
        List<T> update;

        public List<T> getAdd() {
            if (this.add == null) {
                this.add = new ArrayList();
            }
            return this.add;
        }

        public void setAdd(List<T> add) {
            this.add = add;
        }

        public List<T> getDelete() {
            if (this.delete == null) {
                this.delete = new ArrayList();
            }
            return this.delete;
        }

        public void setDelete(List<T> delete) {
            this.delete = delete;
        }

        public List<T> getUpdate() {
            if (this.update == null) {
                this.update = new ArrayList();
            }
            return this.update;
        }

        public void setUpdate(List<T> update) {
            this.update = update;
        }

        public boolean hasChange() {
            return (!CollectionUtils.isEmpty(this.add)) || (!CollectionUtils.isEmpty(this.delete));
        }
    }

    public static enum TM {
        SELF, LIST, MAP, GROUP, MAPLIST, JSON, SEQ;
    }
}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from:
 * D:\eclipseWorkSpace\risen-extsvc\WebRoot\WEB-INF\lib\risen-frame-newest.jar
 * Total time: 47 ms Jad reported messages/errors: Exit status: 0 Caught
 * exceptions:
 */