package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 锁
 */
public class ReentrantLockCounter implements Counter{
    private final java.util.concurrent.locks.ReentrantLock lock = new ReentrantLock();
    private long counter;
    @Override
    public void increment() {
        try {
            lock.lock();
            counter++;
        }finally {
            lock.unlock();
        }
    }
    @Override
    public long getCounter() {
        return this.counter;
    }
}
