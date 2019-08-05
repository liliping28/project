package thread;

/**
 * 线程任务类
 */
public class CounterThread implements Runnable {
    private Counter counter;
    private int number;
    public CounterThread(Counter counter,int number){
        this.number = number;
        this.counter = counter;
    }
    @Override
    public void run() {
        for(int i =0 ; i< number ;i++){
            counter.increment();
        }
    }
}
