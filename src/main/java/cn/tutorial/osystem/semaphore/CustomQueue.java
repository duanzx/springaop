package cn.tutorial.osystem.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**自定义队列
 * 定义容量
 * 定义enqueue和 dequeue方法
 * 定义 List集合用来存储元素
 * 当集合为空时候需要等待存入元素
 * 当集合数量=容量时候，需要等待取出元素
 *
 * */
public class CustomQueue<T> {
    private int CAPACITY = 50;
    private final AtomicInteger INDEX = new AtomicInteger(-1);
    private ReentrantLock lock = new ReentrantLock();
    private Condition fullSignal = lock.newCondition();
    private Condition emptySignal = lock.newCondition();

    private final List<T> list;
    public CustomQueue(){
        list = new ArrayList<>(CAPACITY);
    }

    public void enqueue(T ele){
        try {
            lock.lock();
            if (INDEX.get() == CAPACITY-1){
                fullSignal.await();
            }
            list.add(INDEX.incrementAndGet(),ele);
            emptySignal.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public T dequeue(){
        try{
            lock.lock();
            if (INDEX.get() == -1){
                emptySignal.await();
            }
            T  obj =   list.remove(INDEX.get());
            INDEX.decrementAndGet();
            fullSignal.signal();
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args)throws Exception{
        final CustomQueue<Integer> queue = new CustomQueue<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    int number = new Random().nextInt(999);
                    System.out.println("存入元素："+number);
                    queue.enqueue(number);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread-" + i).start();
            new Thread(() -> {
                try {
                       Integer obj= queue.dequeue();
                       System.out.println("取出元素："+obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "consumer-"+i).start();
        }

    }
}
