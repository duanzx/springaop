package cn.tutorial.osystem.dekker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 让进程预先表明，希望进入临界区的态度，然后再检测临界区状态。
 *
 * */
public class DekkerThird {

    public static void main(String[] args)throws Exception{
        ThreadGroup threadGroup = new ThreadGroup("dekker");
        final AtomicInteger p0useFlag = new AtomicInteger(0); //0未使用，1已使用
        final AtomicInteger p1useFlag = new AtomicInteger(0); //0未使用，1已使用
        for(int i = 0;i<10;i++){
            new Thread(threadGroup, new Runnable() {
                @Override
                public void run() {
                    p0(p0useFlag,p1useFlag);
                }
            },"p0-"+i).start();
            new Thread(threadGroup, new Runnable() {
                @Override
                public void run() {
                    p1(p0useFlag,p1useFlag);
                }
            },"p1-"+i).start();
        }
    }

    public static void p0(final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){
        while(!p0useFlag.compareAndSet(0,1)){

        }
        while (p1useFlag.get() == 1){
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + "等待进入临界区");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        visit();
        while (!p0useFlag.compareAndSet(1,0)){

        }
    }

    public static void p1(final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){
        while(!p1useFlag.compareAndSet(0,1)){

        }
        while (p0useFlag.get() == 1){
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + "等待进入临界区");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        visit();
        while (!p1useFlag.compareAndSet(1,0)){

        }
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
    /**
     * 分析：
     * 可能导致死锁，p0,p1都为true,双方都要进入临界区，互相持有对方的资源，互不相让。
     *
     * */
}
