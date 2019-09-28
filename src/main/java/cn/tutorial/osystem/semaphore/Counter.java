package cn.tutorial.osystem.semaphore;

public class Counter {
    private int b ;
    public Counter(int b){
        this.b = b;
    }
    public void increment(){
        b++;
    }
    public int getB(){
        return b;
    }
}
