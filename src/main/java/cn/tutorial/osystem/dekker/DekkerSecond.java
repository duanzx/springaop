package cn.tutorial.osystem.dekker;
//互斥原则：有则等待，空闲让进，有限等待，让权等待

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可以为临界区设置一个状态标识，表明临界区是否可用
 * 当临界区空闲时候，任何一个进程都能进入，但此时必须修改临界区标志为：被占用
 * 别的进程就不能进入临界区。当临界区使用完毕，必须修改该标志为：空闲。
 * 这样就不再使诸进程严格交替使用临界区，而且，如果某个进程在临界区内/外失败，也不会影响其它进程。
 * */
public class DekkerSecond {
    public static void main(String[] args)throws Exception{
        case1();
    }

    public static void case1() {
        ThreadGroup threadGroup = new ThreadGroup("dekker");
        final AtomicInteger p0useFlag = new AtomicInteger(0); //0未使用，1已使用
        final AtomicInteger p1useFlag = new AtomicInteger(0); //0未使用，1已使用
        for(int i = 0;i<10;i++){
            System.out.println("-----------------"+i+"------------------");
            while (threadGroup.activeCount() > 0){

            }
            new Thread(threadGroup, () -> {
                try {
                    p0(p0useFlag, p1useFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "p0-"+i).start();
            new Thread(threadGroup, () -> {
                try {
                    p1(p0useFlag, p1useFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "p1-"+i).start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void p0(final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){
        while (p1useFlag.get() == 1){ //如果p1已经在使用，就等待
//            System.out.println(Thread.currentThread().getName() + "等待进入临界区");
        }
        while (!p0useFlag.compareAndSet(0,1)){

        }
        System.out.println(Thread.currentThread().getName() + " 开始访问临界区");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束访问临界区");
        while (!p0useFlag.compareAndSet(1,0)){

        }
    }

    public static void p1(final AtomicInteger p0useFlag,final AtomicInteger p1useFlag){
        while (p0useFlag.get() == 1){ //如果p0已经在使用，就等待
//            System.out.println(Thread.currentThread().getName() + "等待进入临界区");
        }
        while (!p1useFlag.compareAndSet(0,1)){

        }
        System.out.println(Thread.currentThread().getName() + " 开始访问临界区");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束访问临界区");
        //ex
        while (!p1useFlag.compareAndSet(1,0)){

        }
    }

    /**
     * 分析：
     * 1. 忙等
     * 2. 若进程在临界区内失败，且响应的flag为true，则其它进程永久阻塞
     * 3. 不能保证进程互斥进入临界区，请按照以下顺序执行
     *  p0 ， if p1flag = false
     *  p0 ,  while p1flag {} .   中断
     *  p1 , if p0flag = false
     *  p1 , while p0flag {}   中断
     *  p0 , set f0flag = true  中断
     *  p1 , set f1flag = true
     *  p0 访问临界区， 跳过了对p1flag的判断
     *  p1 访问临界区， 跳过了对p0flag的判断
     *
     *  进程首先检测临界区状态，若临界区被占用，则忙等。否则就直接进入临界区，从而可能出现同时进入临界区的现象。
     * */


}
