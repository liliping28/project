package thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicCounter 锁
 */
public class AtomicCounter implements Counter{
   private AtomicLong atomicLong = new AtomicLong(0);
    @Override
    public void increment() {
        atomicLong.incrementAndGet();
    }

    @Override
    public long getCounter() {
        return atomicLong.get();
    }
}
