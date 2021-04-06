package concurrent.cyclick;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CyclickBarrierRestTest {
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
        TimeUnit.SECONDS.sleep(4);
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
      cb.reset();//重置会导致Broken exception
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Lock getlock() {
    return lock;
  }
}
