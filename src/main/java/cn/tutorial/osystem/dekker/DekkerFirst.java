package cn.tutorial.osystem.dekker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//进程是可以并发执行的，意味着进程可以在程序执行到任意代码处中断
//初步设想
public class DekkerFirst {
    /**
     * 为了控制两个进程互斥进入临界区，可以让两个进程轮流进入临界区
     * 当一个进程正在临界区执行的时候，另一个进程就不能进入临界区，而在临界区等待
     */

    public static void main(String[] args) throws Exception {
        case1();
    }

    public static void case1(){
        ThreadGroup threadGroup = new ThreadGroup("dekker");
        final AtomicInteger turn = new AtomicInteger(0);
        new Thread(threadGroup, () -> {
            try {
                process0(turn,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "p0-1").start();
        new Thread(threadGroup, () -> {
            try {
                process1(turn,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "p1-1").start();
        while (threadGroup.activeCount() > 0){

        }
        new Thread(threadGroup, () -> {
            try {
                process0(turn,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "p0-2").start();
        new Thread(threadGroup, () -> {
            try {
                process1(turn,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "p1-2").start();
    }

    public static void process0(final AtomicInteger turn,boolean ex)throws Exception {
        while (turn.get() != 0) {
            System.out.println(Thread.currentThread().getName() + "等待进入临界区");
        }
        System.out.println(Thread.currentThread().getName() + " 开始访问临界区");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束访问临界区");
        if(ex){
            throw new Exception();
        }
        while (!turn.compareAndSet(0, 1)) {
        }
    }

    public static void process1(final AtomicInteger turn,boolean ex)throws Exception {
        while (turn.get() != 1) {
            System.out.println(Thread.currentThread().getName() + "等待进入临界区");
        }
        System.out.println(Thread.currentThread().getName() + " 开始访问临界区");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束访问临界区");
        if(ex){
            throw new Exception();
        }
        while (!turn.compareAndSet(1, 0)) {
        }
    }
    /**
     * 原则：
     * 有则等待：只允许一个进程进入临界区，其它进程需要在临界区外等待
     * 空闲让进：如果临界区空闲，只要有进程申请，就立刻让其进入
     * 有限等待：进程只能在临界区逗留有限的时间，不能让临界区外的进程无限等待
     * 让权等待：进程不能在临界区长时间阻塞等待某事件，必须在有限时间内退出临界区
     * 分析：
     * 1. 忙等现象，进程不满足进入临界区条件时，需要无限的空转
     * 2. 进程严格交替进入临界区，如果进程需要多次使用临界区，那么，使用临界区频率低的进程严重制约着使用频率高的进程的执行进度
     * 3. 任何进程在临界区内或临界区外失败，其它进程将可能因为等待使用临界区，而无法向前推进。
     * */
}
