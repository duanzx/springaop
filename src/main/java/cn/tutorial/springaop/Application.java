package cn.tutorial.springaop;

import cn.tutorial.springaop.w3c.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    @Autowired
    private StudentService studentService;

    public static void main(String[] args)throws Exception{
        SpringApplication.run(Application.class,args);
    }

    public void executeAop(){
        StudentService studentService = new StudentService();
        studentService.printInfo(false);
    }
}
