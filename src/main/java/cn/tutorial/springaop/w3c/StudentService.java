package cn.tutorial.springaop.w3c;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    public void printInfo(boolean throwException){
        if (throwException){
            System.out.println("this is a student , throw exception");
            throw new IllegalArgumentException("student exception");
        }
        System.out.println("this is a student");
    }

//    https://blog.csdn.net/forezp/article/details/84927180
//    https://www.jianshu.com/p/269afd0a52e6

}
