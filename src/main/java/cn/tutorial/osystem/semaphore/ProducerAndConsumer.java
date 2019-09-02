package cn.tutorial.osystem.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者问题
 * 1. 生产者和消费者共享同一个缓冲区
 * 2. 生产者和消费者必须同步，生产者生产出数据后，必须通知消费者消费。消费者取出数据消费后，必须通知生产者成产新的数据。
 * 3. 生产者和消费者必须互斥，生产者在生产数据时候，不允许消费者取出数据。消费者在消费数据时候，生产者不能生产。
 * <p>
 * synchronized(Object)  wait()   notify
 * lock condition  await  signal
 */
public class ProducerAndConsumer {

    private static final Integer CACHE_TOTAL = 20;
    private static final String[] cache = new String[CACHE_TOTAL];
    private static final AtomicInteger cacheCount = new AtomicInteger(0);
    private static final Lock cacheLock = new ReentrantLock();
    private static final Condition emptyCondition = cacheLock.newCondition();
    private static final Condition fullConditon = cacheLock.newCondition();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    int number = new Random().nextInt(999);
//                    producer(number + "");
                    producerByWaitNotify(number + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread-" + i).start();
        }
        new Thread(() -> {
            try {
                while (true) {
//                    consumer();
                    consumerByWaitNotify();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();
    }

    public static void producer(String data) throws Exception {
        cacheLock.lock();
        data = Thread.currentThread().getName() + "生产数据" + data;
        if (cacheCount.incrementAndGet() > CACHE_TOTAL) {
            fullConditon.await();
        }
        cache[cacheCount.get()] = data;
        emptyCondition.signal();
        System.out.println(Thread.currentThread().getName() + "添加了数据：" + data);
        cacheLock.unlock();
    }

    public static void consumer() throws Exception {
        cacheLock.lock();
        if (cacheCount.decrementAndGet() < 0) {
            emptyCondition.await();
        }
        String data = cache[cacheCount.get()];
        cache[cacheCount.get()] = null;
        System.out.println(Thread.currentThread().getName() + "取出了数据：index=" + cacheCount.get() + ",content: " + data);
        fullConditon.signal();
        cacheLock.unlock();
    }

    public static void useCountDownLatch() throws Exception {
        final CountDownLatch count = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "-1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count.countDown();
            }, "t-" + i).start();
        }
        count.await();
        System.out.println("所有线程执行完毕");
    }

    /**
     * 分析总结：
     * 1. synchronized与wait notify 的使用
     * wait notify需要搭配 syncronized的使用，其操作目的是基于某种条件，协调多个线程间的运行状态
     * 由于涉及到多个线程间基于共享变量的相互通信，必然需要引入某种同步机制。以确保 wait ,notify 的原子性
     * https://blog.csdn.net/lengxiao1993/article/details/81482410
     * // 线程 A 的代码
     * synchronized(obj_A)
     * {
     * while(!condition){
     * obj_A.wait();
     * }
     * // do something
     * }
     * // 线程 B 的代码
     * synchronized(obj_A)
     * {
     * if(!condition){
     * // do something ...
     * condition = true;
     * obj_A.notify();
     * }
     * }
     */
    private static final Object producerConsumerLock = new Object();
    private static final Object emptyLock = new Object();
    private static final Object fullLock = new Object();

    public static void producerByWaitNotify(String data) throws Exception {
        synchronized (fullLock) {
            if (cacheCount.get() > CACHE_TOTAL-1) {
                fullLock.wait();
            }
        }
        synchronized (producerConsumerLock) {
            data = Thread.currentThread().getName() + "生产数据，index=" + cacheCount.get() + ",content=" + data;
            System.out.println(data);
            cache[cacheCount.incrementAndGet()-1] = data;
        }
        synchronized (emptyLock) {
            emptyLock.notify();
        }
    }

    public static void consumerByWaitNotify() throws Exception {
        synchronized (emptyLock) {
            if (cacheCount.get() <= 0) {
                emptyLock.wait();
            }
        }
        synchronized (producerConsumerLock) {
            int index = cacheCount.decrementAndGet();
            String data = cache[index];
            cache[index] = null;
            System.out.println(Thread.currentThread().getName() + "取出了数据：index=" + index + ",content: " + data);
            producerConsumerLock.notify();
        }
        synchronized (fullLock) {
            fullLock.notify();
        }
    }
    /**
     *  await() signal 是接口Condition的方法，把Object的wait(),notify(),notifyAll分解到了不同的对象中，搭配上任意一种Lock的使用，使得一个对象可以拥有多个等待集。
     *  waitset , 每个Condition都有一个等待集合，该等待集合是一个线程的集合
     * */
}
