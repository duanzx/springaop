package cn.tutorial.springaop;

import cn.tutorial.springaop.w3c.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void executeAop(){
        studentService.printInfo(false);
//        studentService.printInfo(true);
    }
}
