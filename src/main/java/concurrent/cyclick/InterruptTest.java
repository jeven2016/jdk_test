package concurrent.cyclick;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier 对象可能处于一个特殊的状态，称为 broken。当多个线程正在 await() 方法中等待时，
 * 其中一个被中断了，此线程会收到 InterruptedException 异常，但是其他正在等待的线程将收到
 * BrokenBarrierException 异常，并且 CyclicBarrier 会被置于broken 状态中。
 * CyclicBarrier 类提供了isBroken() 方法，如果对象在 broken 状态，返回true，否则返回false。
 */
public class InterruptTest {

  public static void main(String[] args) {

    CyclickBarrierRestTest ctest = new CyclickBarrierRestTest();
    CyclicBarrier cb = new CyclicBarrier(3, () -> {
      String msg = String.format("itemCount=%d\n value=%d\n sum=%d\n",
              ctest.itemCount, ctest.value, ctest.sum);
      System.out.println(msg);
    });
    Thread thread2 = startThread(cb);

    try {
      TimeUnit.SECONDS.sleep(1);

      /**
       * 当thread2被中断后，其他的线程会抛出BrokenException
       */
      thread2.interrupt();

      TimeUnit.SECONDS.sleep(1);
      System.out.println("- cb has been broken " + cb.isBroken());
      System.out.println("- begin to reset and restart....");

      cb.reset();//重置

      startThread(cb);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  private static Thread startThread(CyclicBarrier cb) {
    //创建三个线程，执行完后进行汇总输出
    Thread thread = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
        int index = cb.await();
        System.out.println(Thread.currentThread().getName() + " arrived at " + index);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        //d当CylicBarrier重置后，会使等待状态的进入此exception分支
        System.out.println(Thread.currentThread().getName() + " is broken");
      }
    });

    Thread thread2 = new Thread(() -> {
      try {
        int index = cb.await();
        System.out.println(Thread.currentThread().getName() + " arrived at " + index);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        System.out.println(Thread.currentThread().getName() + " is broken");
      }
    });

    Thread thread3 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
        int index = cb.await();
        System.out.println(Thread.currentThread().getName() + " arrived at " + index);

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        System.out.println(Thread.currentThread().getName() + " is broken");
      }
    });

    thread.start();
    thread2.start();
    thread3.start();
    return thread2;
  }


}
