package concurrent.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ，ReentrantReadWriteLock类有两把锁，一把用于读操作，一把用于写操作。用于读操作的锁，
 * 是通过在 ReadWriteLock接口中声明的readLock()方法获取的。这个锁是实现Lock接口的一个对象，
 * 所以我们可以使用lock()， unlock() 和tryLock()方法。用于写操作的锁，
 * 是通过在ReadWriteLock接口中声明的writeLock()方法获取的。这个锁是实现Lock接 口的一个对象，
 * 所以我们可以使用lock()， unlock() 和tryLock()方法。确保正确的使用这些锁，
 * 使用它们与被设计的目的是一样的，这是程序猿的职责。当你获得Lock接口的读锁时，
 * 不能修改这个变量的值。否则，你可能会有数据不一致的错误。
 * <p/>
 * 此锁最多支持 65535 个递归写入锁和 65535 个读取锁。试图超出这些限制将导致锁方法抛出 Error
 * <p/>
 * 在ReentrantLock类和 ReentrantReadWriteLock类的构造器中，允许一个名为fair的boolean类型参数，
 * 它允许你来控制这些类的行为。默认值为 false，这将启用非公平模式。在这个模式中，当有多个线程正在等待一把锁
 * （ReentrantLock或者 ReentrantReadWriteLock），这个锁必须选择它们中间的一个来获得进入临界区，
 * 选择任意一个是没有任何标准的。true值将开启公平 模式。在这个模式中，当有多个线程正在等待一把锁
 * （ReentrantLock或者ReentrantReadWriteLock），这个锁必须选择它们 中间的一个来获得进入临界区，
 * 它将选择等待时间最长的线程。考虑到之前解释的行为只是使用lock()和unlock()方法。由于tryLock()方
 * 法并不会使线程进入睡眠，即使Lock接口正在被使用，这个公平属性并不会影响它的功能。
 */
public class ReentrantReadWriteLockTest {
  private int num;

  private ReadWriteLock lock = new ReentrantReadWriteLock();

  public static void main(String[] args) {

    ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();

    //虽然读的过程中有等待，但读到的数据均相同，当去掉读锁时则不相同
    testGetAndAddValue(test);

  }

  private static void printLockInfo(ReentrantReadWriteLockTest test) {
    try {
      TimeUnit.SECONDS.sleep(6);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    ReentrantReadWriteLock lock = test.getLock();
    System.out.println("===");
    System.out.println("read lock count = " + lock.getReadLockCount());
    System.out.println("write hold lock count = " + lock.getWriteHoldCount());
    System.out.println("Queue length = " + lock.getQueueLength());
    System.out.println("read hold length = " + lock.getWriteHoldCount());
  }

  private static void testGetAndAddValue(final ReentrantReadWriteLockTest test) {
    Thread thr1 = new Thread() {
      @Override
      public void run() {
        int i = 0;
        while (true) {
          test.addNumber(1);
          i++;

          //每隔十次休眠2秒
          if (i % 10 == 0) {
            try {
              TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    };

    Thread thr2 = new Thread() {
      @Override
      public void run() {
        while (true) {
          test.getNum();
          //获取到值后，休眠2秒
          try {
            TimeUnit.SECONDS.sleep(2);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };

    thr2.start();
    thr1.start();

    try {
      //打印锁的信息
      printLockInfo(test);
      thr2.join();
      thr1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public ReentrantReadWriteLock getLock() {
    return (ReentrantReadWriteLock) lock;
  }

  public void addNumber(int val) {
    lock.writeLock().lock();
    num++;
    lock.writeLock().unlock();
  }

  public int getNum() {
    int val;
    lock.readLock().lock();
    val = num;

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(Thread.currentThread().getName() + " num=" + num + "," +
            "val=" + val);

    lock.readLock().unlock();
    return val;
  }


}
