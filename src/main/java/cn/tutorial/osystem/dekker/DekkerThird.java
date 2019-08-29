package cn.tutorial.osystem.dekker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 让进程预先表明，希望进入临界区的态度，然后再检测临界区状态。
 * */
public class DekkerThird {

    public static void main(String[] args)throws Exception{
        ThreadGroup threadGroup = new ThreadGroup("dekker");
        final AtomicInteger p0useFlag = new AtomicInteger(0); //0未使用，1已使用
        final AtomicInteger p1useFlag = new AtomicInteger(0); //0未使用，1已使用
    }

    public static void p0(final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){

    }

    public static void visit(){
        System.out.println(Thread.currentThread().getName() + " 开始访问临界区");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束访问临界区");
    }
}
