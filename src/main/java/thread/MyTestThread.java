package thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyTestThread {
    /**
     * 测试高并发
     */
    @Test
    public  void myTestThread(){
        //Counter c = new ReentrantLockCounter();
        //Counter c = new SynchronizedCounter();
       Counter c = new AtomicCounter();
        //打印的目标值
        int increments  = 100000;
        //线程数
        int threads = 10000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for(int i=0;i<threads;i++){
            pool.submit(new CounterThread(c , increments));
        }
        long start = System.currentTimeMillis();
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("=====线程池是否已经停止运行："+pool.isTerminated());
            System.out.println("===========总共耗时："+(System.currentTimeMillis()-start));
            System.out.println("======最终值为："+c.getCounter());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
