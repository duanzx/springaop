package cn.tutorial.osystem.semaphore;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 模拟信号量，并实现同步与互斥
 * 两个难点：
 *  CAS修改state
 *  多线程向list中添加head元素
 *  多线程移除list队首元素
 * */
public class SemaphoreSample {
    private final AtomicInteger s;
    private final LinkedBlockingDeque<Thread> list = new LinkedBlockingDeque<>();
    public SemaphoreSample(int count){
        s = new AtomicInteger(count);
    }
    public void waiter()throws Exception{
        s.decrementAndGet();
        if (s.get() < 0){
            list.putFirst(Thread.currentThread());
            LockSupport.park(Thread.currentThread());
        }
    }
    public void signal(){
        s.incrementAndGet();
        if (s.get() <= 0){
            if (!list.isEmpty()){
                Thread thread = list.removeFirst();
                LockSupport.unpark(thread);
            }
        }
    }
    public static void main(String[] args)throws Exception{
        exclusive();
    }

    public static void Synchronization(){

    }
    public static void exclusive (){
        final Semaphore semaphoreSample = new Semaphore(1);
        final Counter counter = new Counter(0);
        ThreadGroup threadGroup = new ThreadGroup("thg");
        for(int i = 0;i<20000;i++){
            new Thread(threadGroup,()->{
                try {
                semaphoreSample.acquire();
                counter.increment();
                semaphoreSample.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while (threadGroup.activeCount() > 0){}
        System.out.println(counter.getB());
    }
    public static void exclusive1 (){
        final SemaphoreSample semaphoreSample = new SemaphoreSample(1);
        final Counter counter = new Counter(0);
        ThreadGroup threadGroup = new ThreadGroup("thg");
        for(int i = 0;i<20000;i++){
            new Thread(threadGroup,()->{
                try {
                    semaphoreSample.waiter();
                    counter.increment();
                    semaphoreSample.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while (threadGroup.activeCount() > 0){}
        System.out.println(counter.getB());
    }
}
