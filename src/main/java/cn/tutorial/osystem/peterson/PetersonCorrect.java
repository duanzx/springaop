package cn.tutorial.osystem.peterson;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 完美实现了双线程互斥的问题
 *
 * */
public class PetersonCorrect {

    public static void main(String[] args)throws Exception{
        ThreadGroup threadGroup = new ThreadGroup("dekker");
        final AtomicInteger turn = new AtomicInteger(1); //0=p0，1=p1
        final AtomicInteger p0useFlag = new AtomicInteger(0); //0未使用，1已使用
        final AtomicInteger p1useFlag = new AtomicInteger(0); //0未使用，1已使用
        new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                while (true){
                    p0(turn,p0useFlag,p1useFlag);
                }
            }
        },"p0").start();
        new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                while (true){
                    p1(turn,p0useFlag,p1useFlag);
                }
            }
        },"p1").start();
    }

    public static void p0(final AtomicInteger turn,final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){
        while (!p0useFlag.compareAndSet(0,1)){

        }
        while (turn.get() == 0 && !turn.compareAndSet(0,1)){

        }
        while (p1useFlag.get() == 1 && turn.get() == 1){

        }
        visit();
        while (!p0useFlag.compareAndSet(1,0)){

        }
    }

    public static void p1(final AtomicInteger turn,final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){
        while (!p1useFlag.compareAndSet(0,1)){

        }
        while (turn.get() == 1 && !turn.compareAndSet(1,0)){

        }
        while (p0useFlag.get() == 1 && turn.get() == 0){

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
}
