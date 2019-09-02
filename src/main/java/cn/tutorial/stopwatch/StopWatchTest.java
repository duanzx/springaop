package cn.tutorial.stopwatch;

import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {

    public static void main(String[] args)throws Exception{
        StopWatch stopWatch = new StopWatch("for test");
        stopWatch.start("mytask");
        TimeUnit.SECONDS.sleep(3);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
