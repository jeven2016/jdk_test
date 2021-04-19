package concurrent.atomic;

import java.util.concurrent.atomic.LongAdder;
import lombok.extern.slf4j.Slf4j;

/*
volatile 解决多线程内存不可见问题。对于一写多读，是可以解决变量同步问题，但是如果多写，同样无法解决线程安全问题。
我们把 volatile 修饰的 count 变量 ++ 10w 次，在启动另一个线程 -- 10w 次，正常来说结果应该是 0，
但是我们执行的结果却为： 1600.  volatile 在多写环境下是非线程安全的

低竞争的并发环境下 AtomicInteger 的性能是要比 LongAdder 的性能好，
而高竞争环境下 LongAdder 的性能比 AtomicInteger 好

AtomicLong可以抛弃了，请使用LongAdder代替（或使用LongAccumulator）
*/
@Slf4j
public class LongAddrTest {

  public static void main(String[] args) {

    LongAdder longAdder = new LongAdder();

    longAdder.add(5);
    longAdder.add(5);
    longAdder.add(5);

    longAdder.decrement();
    longAdder.decrement();
    longAdder.increment();
    log.info("sum: {}", longAdder.sum());
    log.info("long value: {}", longAdder.longValue());
  }
}
