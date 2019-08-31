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
 *
 * synchronized(Object)  wait()   notify
 * lock condition  await  signal
 */
public class ProducerAndConsumer {

    private static final Integer CACHE_TOTAL = 20;
    private static final String[] cache = new String[CACHE_TOTAL];
    private static final AtomicInteger cacheCount = new AtomicInteger(0);
    private static final Lock cacheLock = new ReentrantLock();
    private static final Condition emptyCondition = cacheLock.newCondition();
    private static final  Condition fullConditon = cacheLock.newCondition();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    int number = new Random().nextInt(999);
                    producer(number + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread-" + i).start();
        }
        new Thread(() -> {
            try {
                while (true){
                    consumer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();
    }

    public static void producer(String data) throws Exception {
        cacheLock.lock();
        data = Thread.currentThread().getName() + "生产数据" + data;
        if (cacheCount.incrementAndGet() > CACHE_TOTAL){
            fullConditon.await();
        }
        cache[cacheCount.get()] = data;
        emptyCondition.signal();
        System.out.println(Thread.currentThread().getName() + "添加了数据：" + data);
        cacheLock.unlock();
    }

    public static void consumer() throws Exception {
        cacheLock.lock();
        if (cacheCount.decrementAndGet() < 0){
            emptyCondition.await();
        }
        String data = cache[cacheCount.get()];
        cache[cacheCount.get()] = null;
        System.out.println(Thread.currentThread().getName() + "取出了数据：index="+cacheCount.get()+ ",content: "+ data);
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
}
