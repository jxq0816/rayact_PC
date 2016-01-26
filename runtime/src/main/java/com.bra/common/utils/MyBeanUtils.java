package com.bra.common.utils;import jodd.bean.BeanTool;import org.apache.commons.beanutils.BeanUtilsBean;import org.springframework.util.Assert;import java.lang.reflect.Field;import java.lang.reflect.InvocationTargetException;import java.lang.reflect.Modifier;import java.util.ArrayList;import java.util.List;import java.util.Map;import static jodd.bean.BeanUtil.getDeclaredProperty;import static jodd.bean.BeanUtil.getProperty;/** * 对象操作辅助类 */public class MyBeanUtils {	/*     * private static final Logger log = LoggerFactory	 * .getLogger(MyBeanUtils.class);	 */    /**     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.     */    @SuppressWarnings("unchecked")    public static <T> T getFieldValue(final Object object,                                      final String fieldName) {        return (T) getDeclaredProperty(object, fieldName);    }    @SuppressWarnings("unchecked")    public static <T> T getGetFieldValue(final Object object,                                         final String fieldName) {        return (T) getProperty(object, fieldName);    }    public static Object getGetFieldObjValue(final Object object,                                             final String fieldName) {        return getProperty(object, fieldName);    }    @SuppressWarnings("unchecked")    public static Map<String, Object> describe(Object bean) {        if (bean instanceof Map) {            return (Map<String, Object>) bean;        }        try {            return BeanUtilsBean.getInstance().getPropertyUtils().describe(bean);        } catch (IllegalAccessException e) {            throw new RuntimeException("never happend exception!", e);        } catch (InvocationTargetException e) {            throw new RuntimeException("never happend exception!", e);        } catch (NoSuchMethodException e) {            throw new RuntimeException("never happend exception!", e);        }    }    /**     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.     */    public static void setFieldValue(final Object object,                                     final String fieldName, final Object value) {        Field field = getDeclaredField(object, fieldName);        if (field == null) {            throw new IllegalArgumentException("Could not find field ["                    + fieldName + "] on target [" + object + "]");        }        makeAccessible(field);        try {            field.set(object, value);        } catch (IllegalAccessException e) {            throw new RuntimeException("never happend exception!", e);        }    }    /**     * 循环向上转型,获取对象的DeclaredField.     */    protected static Field getDeclaredField(final Object object,                                            final String fieldName) {        Assert.notNull(object);        return getDeclaredField(object.getClass(), fieldName);    }    /**     * 循环向上转型,获取类的DeclaredField.     */    @SuppressWarnings("rawtypes")    protected static Field getDeclaredField(final Class clazz,                                            final String fieldName) {        Assert.notNull(clazz);        Assert.hasText(fieldName);        for (Class superClass = clazz; superClass != Object.class; superClass = superClass                .getSuperclass()) {            try {                return superClass.getDeclaredField(fieldName);            } catch (NoSuchFieldException e) {                // Field不在当前类定义,继续向上转型            }        }        return null;    }    /**     * 强制转换fileld可访问.     */    protected static void makeAccessible(final Field field) {        if (!Modifier.isPublic(field.getModifiers())                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {            field.setAccessible(true);        }    }    public static Object getSimpleProperty(Object bean, String propName)            throws IllegalArgumentException, SecurityException,            IllegalAccessException, InvocationTargetException,            NoSuchMethodException {        return getProperty(bean, propName);    }    public static List<Object> getPropertyByList(List<?> list, String propName)            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {        List<Object> returnV = new ArrayList<Object>();        for (Object o : list) {            Object v = getSimpleProperty(o, propName);            returnV.add(v);        }        return returnV;    }    public static void copyProperties(Object dest, Object orig) {        BeanTool.copy(dest, orig);    }    private static class StrUtils {    }}