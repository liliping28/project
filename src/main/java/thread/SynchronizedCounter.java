package thread;

/**
 * 同步锁
 */
public class SynchronizedCounter implements Counter{
    private long counter;
    @Override
    public synchronized void increment() {
        counter++;
    }

    @Override
    public long getCounter() {
        return this.counter;
    }
}
