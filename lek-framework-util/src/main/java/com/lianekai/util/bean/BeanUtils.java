package com.lianekai.util.bean;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lianekai.util.cache.Cache;
import com.lianekai.util.common.Constant;
import com.lianekai.util.math.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Bean操作工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/19 15:37
 */
@Slf4j
public class BeanUtils {
    private BeanUtils(){}

    private static final List<String> IGNORES = new ArrayList<>();
    private static Cache<Class<?>, BeanInfo> beanInfoCache = new Cache<>(32);

    /**
     * BeanCopier缓存
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    static {
        IGNORES.add("class");
        IGNORES.add("hibernateLazyInitializer");
        IGNORES.add("handler");
        IGNORES.add("fieldHandler");
    }

    public static <T> T copyBean(Object source, T target) {
        return copyBean(source, target, null);
    }

    public static <T> T copyBean(Object source, T target, String ignore) {
        Map<String, Object> map = toMap(source, ignore);
        return copyBean(map, target);
    }

    public static <T> T copyBeanNotNull(Object source, T target) {
        Map<String, Object> map = toMapNotNull(source);
        return copyBeanNotNull(map, target);
    }
    public static <T> T copyBean(Map<String, Object> source, T target) {
        BeanInfo beanInfo = getBeanInfo(target.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (IGNORES.contains(name) || !source.containsKey(name)) {
                continue;
            }
            Object value = source.get(name);
            setValue(target, pd.getWriteMethod(), value);
        }
        return target;
    }

    public static <T> T copyBeanNotNull(Map<String, Object> source, T target) {
        BeanInfo beanInfo = getBeanInfo(target.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (IGNORES.contains(pd.getName())) {
                continue;
            }
            Object value = source.get(pd.getName());
            if (value != null) {
                setValue(target, pd.getWriteMethod(), value);
            }
        }
        return target;
    }

    public static void writeEmptyStringAsNull(Object obj) {
        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (IGNORES.contains(pd.getName())) {
                continue;
            }
            Object value = getValue(obj, pd.getReadMethod());
            if ("".equals(value)) {
                setValue(obj, pd.getWriteMethod(), null);
            }
        }
    }
    public static Map<String, Object> toMap(Object obj, String ignore) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (IGNORES.contains(name)  || (ignore != null && ignore.contains(name)) ) {
                continue;
            }
            Object value = getValue(obj, pd.getReadMethod());
            result.put(name, value);
        }
        return result;
    }

    //todo
//    public static <T> T toBean(Map<String, Object> map, Class<T> clazz){
//        return null;
//    }

    public static Map<String, Object> toMapNotNull(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (IGNORES.contains(name)) {
                continue;
            }

            Object value = getValue(obj, pd.getReadMethod());
            if (value != null) {
                result.put(name, value);
            }
        }
        return result;
    }

    public static Object getValue(Object obj, String name, Object defValue) {
        Object value = getValue(obj, name);
        if (value != null) {
            return value;
        }
        return defValue;
    }

    public static Object getValue(Object obj, String name) {
        if (obj == null) {
            return null;
        }
        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (name.equals(pd.getName())) {
                return getValue(obj, pd.getReadMethod());
            }
        }

        return null;
    }

    public static <T,R> R getValue(T obj, Method getter) {
        if (getter != null && obj!=null) {
            try {
                return (R)getter.invoke(obj);
            } catch (Exception e) {
                log.error("获取对象值失败", e);
            }
        }
        return null;
    }
    public static void setValue(Object obj, String name, Object value) {
        if (obj == null) {
            return;
        }

        BeanInfo beanInfo = getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (name.equals(pd.getName())) {
                setValue(obj, pd.getWriteMethod(), value);
                return;
            }
        }

    }

    public static void setValue(Object obj, Method setter, Object value) {

        if (setter != null) {
            if (value != null && !setter.getParameterTypes()[0].isAssignableFrom(value.getClass())) {
                log.error("设置对象值失败,类型不匹配{}，值{}", setter, value);
                return;
            }

            try {
                setter.invoke(obj, value);
            } catch (Exception e) {
                log.error("设置对象值失败", e);
            }
        }
    }

    public static Method getReadMethod(Class<?> key, String propertyName) {
        return getPropertyDescriptor(key, propertyName).getReadMethod();
    }

    public static Method getWriteMethod(Class<?> key, String propertyName) {
        return getPropertyDescriptor(key, propertyName).getWriteMethod();
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> key, String propertyName) {
        BeanInfo beanInfo = getBeanInfo(key);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(propertyName)) {
                return pd;
            }
        }
        throw new RuntimeException("NoSuchProperty:" + propertyName);
    }
    public static BeanInfo getBeanInfo(Class<?> key) {
        return beanInfoCache.get(key, () -> {
            BeanInfo info = Introspector.getBeanInfo(key);
            Introspector.flushCaches();
            return info;
        });
    }

    public static <T> T newInstance(Class<T> target) {
        try {
            return target.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成供测试使用的Bean对象
     * 对象内部的属性均为随机数，默认对象深度为2
     */
    public static <T> T genBean(Class<T> target) throws Exception {
        return genBean(target, 2);
    }

    /**
     * 生成供测试使用的Bean对象
     * 对象内部的属性均为随机数，默认对象深度为2
     *
     * @param target 目标类类型
     * @param depth  对象深度（若对象内存在其他对象，深度则控制对象内对象的嵌套层数）
     */
    public static <T> T genBean(Class<T> target, int depth) throws Exception {
        if (depth-- < 1) {
            return null;
        }
        T result = target.newInstance();

        PropertyDescriptor[] pds = getBeanInfo(target).getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            Class<?> clazz = pd.getPropertyType();
            if (clazz.isAssignableFrom(Class.class)) {
                continue;
            }
            Object value = genBaseData(clazz, depth, target, pd.getName());
            setValue(result, pd.getWriteMethod(), value);
        }
        return result;
    }

    private static <T> Object genBaseData(Class<T> clazz, int depth, Class<?> rootClass, String name) throws Exception {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(Boolean.TYPE)) {
            return random.nextBoolean();
        }
        if (clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(Byte.TYPE)) {
            return (byte) random.nextInt();
        }
        if (clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(Short.TYPE)) {
            return (short) random.nextInt();
        }
        if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(Integer.TYPE)) {
            return random.nextInt();
        }
        if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(Long.TYPE)) {
            return random.nextLong();
        }
        if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(Float.TYPE)) {
            return random.nextFloat();
        }
        if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(Double.TYPE)) {
            return random.nextDouble();
        }
        if (clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(Character.TYPE)) {
            return RandomUtils.getRandomString(1).charAt(0);
        }

        if (clazz.isAssignableFrom(String.class)) {
            return RandomUtils.getRandomString(9);
        }


        if (clazz.isAssignableFrom(Date.class)) {
            return new Date();
        }
        if (clazz.isAssignableFrom(List.class)) {
            Class<?> x = getGenericType(rootClass, name);
            Object o = genBaseData(x, depth, null, null);

            ArrayList<Object> list = new ArrayList<>(2);
            list.add(o);
            list.add(o);
            return list;
        }
        if (clazz.isArray()) {
            Object array = Array.newInstance(clazz.getComponentType(), 2);
            Object o = genBaseData(clazz.getComponentType(), depth, null, null);
            Array.set(array, 0, o);
            Array.set(array, 1, o);
            return array;
        }
        if (clazz.isEnum()) {
            Method method = clazz.getMethod("values");
            Object[] values = (Object[]) method.invoke(null);
            return values[random.nextInt(values.length)];
        }
        return genBean(clazz, depth);
    }

    /**
     * 获得泛型属性的泛型类型
     *
     * @param rootClass 根对象
     * @param fieldName 属性名
     * @return
     */
    public static Class<?> getGenericType(Class<?> rootClass, String fieldName) {
        try {
            Field field = rootClass.getDeclaredField(fieldName);
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            return (Class) parameterizedType.getActualTypeArguments()[0];
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据属性名获取属性元素，包括各种安全范围和所有父类
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static Field getFieldByClasss(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入

            }
        }
        return field;

    }

    public static List<Field> getFieldByAnnotation(Class<? extends Annotation> annotation, Class<?> clazz) {
        List<Field> resultList=new ArrayList<>();
        for (; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields=clazz.getDeclaredFields();
                Arrays.stream(fields).forEach(item->{
                    if(item.isAnnotationPresent(annotation))
                    {
                        resultList.add(item);
                    }
                });
            } catch (Exception e) {
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return resultList;

    }

    public static Field getOneFieldByAnnotation(Class<?> clazz,Class<? extends Annotation> annotation) {
        AtomicReference<Field> field=new AtomicReference<>();
        for (; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields=clazz.getDeclaredFields();
                Arrays.stream(fields).forEach(item->{
                    if(item.isAnnotationPresent(annotation))
                    {
                        field.set(item);
                    }
                });
            } catch (Exception e) {
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return field.get();

    }

    public static Method getMethodByName(Class<?> clazz,String methodName)
    {
        Method method=null;
        try {
            method=clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.error("",e);
        }
        return method;
    }

    public static Method getReadMethodByAnnotation(Class<?> clazz,Class<? extends Annotation> annotation)
    {
        if(clazz==null)
        {
            return null;
        }
        Field field=getOneFieldByAnnotation(clazz,annotation);
        if(field==null)
        {
            return null;
        }
        return getReadMethod(clazz,field.getName());
    }

    public static Method getWriteMethodByAnnotation(Class<?> clazz,Class<? extends Annotation> annotation)
    {
        if(clazz==null)
        {
            return null;
        }
        Field field=getOneFieldByAnnotation(clazz,annotation);
        if(field==null)
        {
            return null;
        }
        return getWriteMethod(clazz,field.getName());
    }

    public static Object getValueByFieldName(String fieldName, Object object) {
        Object value = null;
        Field field = getFieldByClasss(fieldName, object);
        if(field==null)
        {
            return null;
        }
        try {
            ReflectionUtils.makeAccessible(field);
            value = ReflectionUtils.getField(field,object);
        } catch (IllegalArgumentException e) {
            log.error("", e);
        }
        return value;
    }


    public static <T> T copyPropertiesForInsert(Object source,Class<T> targetClazz){
        T target = ClassUtils.newInstance(targetClazz);
        org.springframework.beans.BeanUtils.copyProperties(source,target, Constant.INSERT_IGNORE_FIELD);
        return target;
    }

    public static <T> T copyPropertiesForUpdate(Object source,Class<T> targetClazz){
        T target = ClassUtils.newInstance(targetClazz);
        org.springframework.beans.BeanUtils.copyProperties(source,target, Constant.UPDATE_IGNORE_FIELD);
        return target;
    }


    /**
     * List中null的字符串 设置为 空字符串
     * @return current instance collection
     */
    public static Collection<?> writeNullStringAsEmpty(Collection<?> collection) {
        return collection.stream().map(BeanUtils::writeNullStringAsEmpty).collect(Collectors.toList());
    }

    /**
     * 把对象中的为null的字符串设置为空字符
     * @param t
     *            instance
     * @param <T>
     *            Object
     * @return current instance t
     */
    public static <T> T writeNullStringAsEmpty(T t) {
        return JSONObject.parseObject(JSON.toJSONString(t, SerializerFeature.WriteNullStringAsEmpty),
                (Type)t.getClass());
    }

    /**
     * 拷贝对象属性<br>
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        if (ObjectUtil.isNull(source)) {
            return;
        }
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    /**
     * 拷贝对象属性<br>
     *
     * @param <T>
     * @param source
     * @param targetClass
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }

        T t = null;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(String.format("BeanCopier -> Create new instance of %s failed.", targetClass.getName()), e);
            throw new RuntimeException(
                    String.format("BeanCopier -> Create new instance of %s failed.", targetClass.getName()));
        }
        copyProperties(source, t);
        return t;
    }

    /**
     * 获取BeanCopier<br>
     * @param sourceClass
     * @param targetClass
     * @return
     */
    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        } else {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        }
        return copier;
    }

    /**
     * 类名拼接构成Key<br>
     *
     * @param sourceClass
     * @param targetClass
     * @return
     */
    private static String generateKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.getName() + targetClass.getName();
    }
}
