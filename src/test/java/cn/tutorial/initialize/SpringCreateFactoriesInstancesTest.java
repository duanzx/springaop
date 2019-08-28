package cn.tutorial.initialize;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

public class SpringCreateFactoriesInstancesTest {
// 参考 spring createSpringFactoriesInstances
//    for (String name : names) {
//        try {
//            Class<?> instanceClass = ClassUtils.forName(name, classLoader);
//            Assert.isAssignable(type, instanceClass);
//            Constructor<?> constructor = instanceClass
//                    .getDeclaredConstructor(parameterTypes);
//            T instance = (T) BeanUtils.instantiateClass(constructor, args);
//            instances.add(instance);
//        }
//        catch (Throwable ex) {
//            throw new IllegalArgumentException(
//                    "Cannot instantiate " + type + " : " + name, ex);
//        }
//    }

    @Test
    public void testCreateInstance()throws Exception {
//        Person person = createInstance(Person.class,"cn.tutorial.initialize.Person",new Class[]{String.class},new Object[]{"duanzx"});
//        System.out.println(person);
    }

    @Test
    public void testArraysInstance(){
        String[] strings = (String[])Array.newInstance(String.class,10);
        strings[0] = "aa";
        System.out.println(strings[0]);
    }

    @Test
    // Assert.isAssignable 两个class类型是否匹配
    public void testIsAssignable(){
        Assert.isAssignable(String.class,String.class);
    }

    private <T> T createInstance(Class<T> type, String className, Class<?>[] parameterTypes, Object[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> instanceClass = ClassUtils.forName(className, classLoader);
        Constructor<?> constructor = instanceClass.getDeclaredConstructor(parameterTypes);
        T instance = (T) BeanUtils.instantiateClass(constructor, args);
        return instance;
    }

//    public static Class<?> forName(String name, @Nullable ClassLoader classLoader)
//            throws ClassNotFoundException, LinkageError {
//
//        Assert.notNull(name, "Name must not be null");
//        //根据基本类的JVM命名规则(如果合适的话)，将给定的类名name解析为基本类型的包装类
//        Class<?> clazz = resolvePrimitiveClassName(name);
//        if (clazz == null) {
//            //commonClassCache是包含java.lang包下所有类，将类的类名作为键，对应类作为值的一个Map集合。
//            clazz = commonClassCache.get(name); //根据类名，获取commonClassCache集合中的值，如果为空，表示目标类不是java.lang包的下类，即不是原始类型。
//        }
//        if (clazz != null) {
//            //如果根据类名，已经获取到了类，则直接返回该类。
            //如果本地类（基本类型类，其他），不需要解析，直接返回
//            return clazz;
//        }
//
//        //判断clas属性值是否为数组对象。比如：java.lang.String[]
//        // "java.lang.String[]" style arrays
//        if (name.endsWith(ARRAY_SUFFIX)) {
//            //如果是，则将类名后的“[]”方括号截去，返回java.lang.String,递归查找类名，找到后，将Class类型转换为数组。
//            String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
//            Class<?> elementClass = forName(elementClassName, classLoader); //找到数组元素的class类型
//            return Array.newInstance(elementClass, 0).getClass();
//        }
//
//        //class属性值是否为数组对象的二进制表示。比如：[Ljava.lang.String
//        // "[Ljava.lang.String;" style arrays
//        if (name.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && name.endsWith(";")) {
//            //如果是，则将值的“[L”部分截去，递归查找类名，找到后，将对应的Class类型转换为数组
//            String elementName = name.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), name.length() - 1);
//            Class<?> elementClass = forName(elementName, classLoader);
//            return Array.newInstance(elementClass, 0).getClass();
//        }
//
//        //class属性值是否为二维数组
//        // "[[I" or "[[Ljava.lang.String;" style arrays
//        if (name.startsWith(INTERNAL_ARRAY_PREFIX)) {
//            String elementName = name.substring(INTERNAL_ARRAY_PREFIX.length());
//            Class<?> elementClass = forName(elementName, classLoader);
//            return Array.newInstance(elementClass, 0).getClass();
//        }
//
//        //获取classLoader
//        ClassLoader clToUse = classLoader;
//        if (clToUse == null) {
//            //如果classLoader为空，则获取默认的classLoader对象。
//            clToUse = getDefaultClassLoader();
//        }
//        try {
//            //返回加载后的类
//            return (clToUse != null ? clToUse.loadClass(name) : Class.forName(name));
//        }
//        catch (ClassNotFoundException ex) {
//            //用于处理内部类的情况。
//            int lastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
//            if (lastDotIndex != -1) {
//                //拼接内部类的名字。
//                String innerClassName = name.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR + name.substring(lastDotIndex + 1);
//                try {
//                    return (clToUse != null ? clToUse.loadClass(innerClassName) : Class.forName(innerClassName));
//                }
//                catch (ClassNotFoundException ex2) {
//                    // Swallow - let original exception get through
//                }
//            }
//            throw ex;
//        }
//    }

}
