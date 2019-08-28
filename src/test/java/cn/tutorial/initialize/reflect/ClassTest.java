package cn.tutorial.initialize.reflect;

import org.junit.Test;
import org.springframework.util.Assert;

import java.lang.reflect.Modifier;

/*
*
* */
public class ClassTest {
        private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    getClass() 返回引用类对象的Class对象
    public void testGetClass(){
        ClassTest classTest = new ClassTest();
        Class clazz = classTest.getClass();
    }
    // forName()根据类路径，返回对Class对象的引用
    @Test
    public void testForName()throws Exception{
        Class clazz = Class.forName("cn.tutorial.initialize.reflect.ClassTest");
        Class c1 = ClassTest.class;
        Class c2 = new ClassTest().getClass();
        Assert.isAssignable(clazz,c1);
        Assert.isAssignable(clazz,c2);
    }
    //获取类的信息，包名称，访问修饰符 , getInterfaces()
    @Test
    public void testGetClassInfo(){
        Class clazz = ClassTest.class;
        System.out.println(clazz.getSimpleName());
        System.out.println(Modifier.toString(clazz.getModifiers()));
        System.out.println(clazz.getSuperclass().getSimpleName());
    }
}
