package concurrent.cyclick;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CyclicBarrier 类有一个整数初始值，此值表示将在同一点同步的线程数量。当其中一个线程到达确定点，它会调用await() 方法来等待其他线程。
 * 当线程调用这个方法，CyclicBarrier阻塞线程进入休眠直到其他线程到达。当最后一个线程调用CyclicBarrier 类的await() 方法，它唤醒所有等
 * 待的线程并继续执行它们的任务。
 * <p/>
 * CyclicBarrier 类有个有趣的优势是，你可以传递一个外加的 Runnable 对象作为初始参数，并且当全部线程都到达同一个点时，CyclicBarrier类
 * 会把这个对象当做线程来执行。此特点让这个类在使用 divide 和 conquer 编程技术时，可以充分发挥任务的并行性，
 * <p/>
 * CyclicBarrier 用于这种场景，先执行完几个并行的线程运算，然后最终合并线程运行进行合并计算。
 */
public class CyclickBarrierTest {
  private static ConcurrentLinkedQueue<Integer> valueStream = new ConcurrentLinkedQueue();

  static {
    for (int i = 0; i < 10; i++) {
      valueStream.offer(new Random().nextInt(100));
    }
  }

  public int itemCount; //有多少成员
  public int value; //查找的值
  public int sum;  //总和
  private ReentrantLock lock = new ReentrantLock();

  public static void main(String[] args) {
    CyclickBarrierRestTest ctest = new CyclickBarrierRestTest();
    CyclicBarrier cb = new CyclicBarrier(3, () -> {
      String msg = String.format("itemCount=%d\n value=%d\n sum=%d\n",
              ctest.itemCount, ctest.value, ctest.sum);
      System.out.println(msg);
    });

    //创建三个线程，执行完后进行汇总输出
    Thread thread = new Thread(() -> {
//      ctest.getlock().lock();
      try {
        TimeUnit.SECONDS.sleep(2);
        //计算出有多少成员
        ctest.itemCount = valueStream.size();
//        ctest.getlock().unlock();
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
//      ctest.getlock().lock();
      try {
        TimeUnit.SECONDS.sleep(1);
        //查找成员
        ctest.value = valueStream.peek();
//        ctest.getlock().unlock();
        int index = cb.await();
        System.out.println(Thread.currentThread().getName() + " arrived at " + index);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        System.out.println(Thread.currentThread().getName() + " is broken");
      }
    });

    Thread thread3 = new Thread(() -> {
//      ctest.getlock().lock();
      try {
        TimeUnit.SECONDS.sleep(3);
        //查找成员
        ctest.sum = valueStream.stream().mapToInt(val -> val).sum();
//        ctest.getlock().unlock();
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

    try {
      TimeUnit.SECONDS.sleep(1);
      statsInfo(cb);
//      TimeUnit.SECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void statsInfo(CyclicBarrier cb) {
    System.out.println("===========================================");
    System.out.println("count of waiting :" + cb.getNumberWaiting());
    System.out.println("Parties :" + cb.getParties());
    System.out.println("===========================================");
  }
}
