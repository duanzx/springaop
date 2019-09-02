package cn.tutorial;

import cn.tutorial.eventlistener.CustomEvent;
import cn.tutorial.eventlistener.CustomEventListener;
import cn.tutorial.springaop.w3c.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    @Autowired
    private StudentService studentService;

//    @EventListener注解主要通过EventListenerMethodProcessor扫描出所有带有@EventListener注解的方法，
// 然后动态构造事件监听器，并将监听器托管到Spring应用上文中。

//    Spring-boot-{version}.jar包中提供一个类DelegatingApplicationListener，
// 该类的作用是从application.properties中读取配置context.listener.classes，并将事件广播给这些配置的监听器。

    public static void main(String[] args)throws Exception{
        //SpringApplicationRunListener
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new CustomEventListener());
        ConfigurableApplicationContext context = springApplication.run(args);
        //SimpleApplicationEventMulticaster
        context.publishEvent(new CustomEvent(new Object()));
        // getApplicationListeners(event, type) 筛选监听器，
        // 在context.publish(ApplicationEvent event)中已经将事件传入，
        // getApplicationListeners中将可以根据这个event类型从Spring容器中检索出符合条件的监听器(可能为多个)
        //尝试逐个向监听器广播
    }

    public void executeAop(){
        StudentService studentService = new StudentService();
        studentService.printInfo(false);
    }

    //todo 仿写Spring Event MultiCaster ， 手写观察者模式，设计模式
}
