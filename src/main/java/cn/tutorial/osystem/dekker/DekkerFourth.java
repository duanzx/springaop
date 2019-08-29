package cn.tutorial.osystem.dekker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 允许进程既表明需要进入临界区的态度，又能相互谦让。
 * 首先表示自己需要使用临界区，再检测临界区的状态，若临界区被占用，可以放弃自己的权力，等一段时间再申请
 *
 * */
public class DekkerFourth {

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
                while(!p0useFlag.compareAndSet(1,0)){

                }
                System.out.println(Thread.currentThread().getName() + "放弃自己的权力等待进入临界区");
                TimeUnit.SECONDS.sleep(2);
                while(!p0useFlag.compareAndSet(0,1)){

                }
                System.out.println(Thread.currentThread().getName() + "争取到自己的权力进入临界区");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        visit();
        while (!p0useFlag.compareAndSet(1,0)){

        }
    }

    public static void p1(final AtomicInteger p0useFlag, final AtomicInteger p1useFlag){
        while(!p1useFlag.compareAndSet(0,1)){

        }
        while (p0useFlag.get() == 1){
            try {
                while(!p1useFlag.compareAndSet(1,0)){

                }
                System.out.println(Thread.currentThread().getName() + "放弃自己的权力等待进入临界区");
                TimeUnit.SECONDS.sleep(2);
                while(!p1useFlag.compareAndSet(0,1)){

                }
                System.out.println(Thread.currentThread().getName() + "争取到自己的权力进入临界区");
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
     * PO, P1的谦让，可能使它们都不能进入临界区
     * 这种现象不是死锁，因为这种僵局不会是永久行为，某一时刻可能会自动解除
     * 当时这种现象也是不希望出现的
     *
     * */
}
