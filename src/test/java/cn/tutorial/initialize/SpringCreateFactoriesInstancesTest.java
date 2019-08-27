package cn.tutorial.initialize;

import org.junit.Test;

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
    public void testCreateInstance(){

    }

    private void createInstance(String className){

    }

}
