package concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StampedLockTest {
  private static final Map<String, String> cache = new HashMap<>();
  private static final StampedLock lock = new StampedLock();

  public static void main(String[] args) {
    StampedLockTest test = new StampedLockTest();
    test.test1();
  }

  /*
   thread1 get value: null
   writeThread get write lock
   thread1 convert to read lock
   writeThread finished set key
   thread1 get read lock
   thread1 get value again: hello
  */
  public void test1() {
    var thread1 =
        new Thread(
            () -> {
              var val = read("key");
              log.info("thread1 get value: {}", val);

              // wait for 2 seconds and ensure the write lock is working
              try {
                TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              // try to read (would be blocked for a while)
              val = read("key");
              log.info("thread1 get value again: {}", val);
            },
            "thread1");

    var writeThread =
        new Thread(
            () -> {
              set("key", "hello", 4);
              log.info("writeThread finished set key");
            },
            "writeThread");

    thread1.start();
    writeThread.start();
  }

  public String read(String key) {
    var stamp = lock.tryOptimisticRead(); // non-blocking thread
    var value = cache.get(key);
    var threadName = Thread.currentThread().getName();

    // 校验是否被其他线程修改过,true 表示未修改，否则需要加悲观读锁
    if (!lock.validate(stamp)) {
      log.info("{} convert to read lock", threadName);

      // acquiring the read lock
      // 上悲观读锁，并重新读取数据到当前线程局部变量, thread will wait for lock
      stamp = lock.readLock();
      log.info("{} get read lock", threadName);
      value = cache.get(key);
      lock.unlock(stamp);
    }

    // 若校验通过，则直接返回数据
    return value;
  }

  public void set(String key, String value, long seconds) {
    var threadName = Thread.currentThread().getName();
    var stamp = lock.readLock();
    try {
      while (cache.get(key) == null) {
        stamp = lock.tryConvertToWriteLock(stamp); // 读锁转换为写锁
        // 不为 0 升级写锁成功
        if (stamp != 0L) {
          log.info("{} get write lock", threadName);

          // wait for some minutes
          if (seconds > 0) {
            try {
              TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

          cache.put(key, value);
          break;
        } else {
          log.info("{} cannot get write lock , will try again", threadName);
          lock.unlockRead(stamp); // 转换失败释放读锁
          stamp = lock.writeLock(); // 强制获取写锁
          // repreat in next loop
        }
      }
    } finally {
      lock.unlock(stamp);
    }
  }
}
