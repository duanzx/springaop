package cn.tutorial.springaop.w3c;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    /**
     * 声明一个切入点
     * 一个切入点有助于确定使用不同建议执行的感兴趣的连接点（即方法）。在处理基于配置的 XML 架构时，切入点的声明有两个部分：
     * 一个切入点表达式决定了我们感兴趣的哪个方法会真正被执行。
     * 一个切入点标签包含一个名称和任意数量的参数。方法的真正内容是不相干的，并且实际上它应该是空的。
     * 声明@Pointcut("execution(* com.tutorialspoint.Student.getName(..))")
     * execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
     * private void getname() {}
     */

//在cn.tutorial.springaop.w3c包下的任意类下的任意返回值和任意参数方法
    @Pointcut("execution(* cn.tutorial.springaop.w3c.*.*(..))")
    private void selectAll() {
    }

    @Before(value = "selectAll()")
    public void beforeAdvice() {
        System.out.println("Going to setup student profile");
    }

    @After(value = "selectAll()")
    public void afterAdvice(){
        System.out.println("Student profile has been setup");
    }

    @AfterReturning(pointcut = "selectAll()",returning = "returnVal")
    public void afterReturningAdvice(Object returnVal){
//        System.out.println("Returning: "+null == returnVal?"null":returnVal.toString());
        System.out.println("return..."+(null == returnVal?"null":returnVal.toString()));
    }

    @AfterThrowing(pointcut = "selectAll()",throwing = "ex")
    public void  AfterThrowingAdvice(IllegalArgumentException ex){
        System.out.println("Three has been an exception: "+ex.toString());
    }


}
