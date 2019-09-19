package cn.tutorial.threadlocal;

/**
 * 使用多个线程用同一个变量进行计数
 *
 * */
public class ThreadLocalSample {

    public static void main(String[] args)throws Exception{
       final ThreadLocal<Integer> counter = new ThreadLocal<>();
        new Thread(()->{
            for(int i = 0;i<10;i++){
                counter.set(i);
                System.out.println(Thread.currentThread().getName()+" - " + counter.get());
            }
        },"thread-1").start();
        new Thread(()->{
            for(int i = 10;i<20;i++){
                counter.set(i);
                System.out.println(Thread.currentThread().getName()+" - " + counter.get());
            }
        },"thread-2").start();
    }
}
