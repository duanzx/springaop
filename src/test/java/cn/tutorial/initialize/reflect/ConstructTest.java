package cn.tutorial.initialize.reflect;


import org.junit.Test;

/**
 * 构造函数反射
 * getConstructors() 方法返回当前和超类的所有公共构造函数
 * getDeclaredConstructors() 方法返回当前类的所有声明的构造函数
 * (Class ... parameterTypes) 通过参数类型获取构造函数对象
 * */
public class ConstructTest {
    private String name;
    public ConstructTest(){

    }

    public ConstructTest(String name){
        this.name = name;
    }

    @Test
    public void test(){

    }
}
