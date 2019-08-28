package cn.tutorial.initialize;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

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
        Person person = createInstance(Person.class,"cn.tutorial.initialize.Person",new Class[]{String.class},new Object[]{"duanzx"});
        System.out.println(person);
    }

    //todo  Assert.isAssignable ?
    private <T> T createInstance(Class<T> type, String className, Class<?>[] parameterTypes, Object[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> instanceClass = ClassUtils.forName(className, classLoader);
        Constructor<?> constructor = instanceClass.getDeclaredConstructor(parameterTypes);
        T instance = (T) BeanUtils.instantiateClass(constructor, args);
        return instance;
    }

}
