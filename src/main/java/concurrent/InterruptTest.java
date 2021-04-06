package concurrent;

/**
 * Interrupt test
 */
public class InterruptTest {

  public static void main(String[] args) {

//    testinterrupt_normal();
    testinterrupt_Exception();
  }

  private static void testinterrupt_Exception() {
    Thread thread = new Thread(new NumberTrackerSleeper());
    thread.start();

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    thread.interrupt();
    System.out.println("main exit.");
  }

  /**
   *  wait()、wait(long) 或 wait(long, int) 方法，或者该类的 join()、join(long)、join(long, int)、sleep(long) 或 sleep(long, int)
   *  方法过程中受阻，则其中断状态将被清除，它还将收到一个 InterruptedException
   */
  private static void testinterrupt_normal() {
    Thread thread = new Thread(new NumberTracker());
    thread.start();

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    thread.interrupt();
    System.out.println("main exit.");
  }
}

class NumberTracker implements Runnable {

  @Override
  public void run() {
    Thread thread = Thread.currentThread();
    int num = 0;
    while (!thread.isInterrupted()) {
      System.out.printf("%s  , number is %d \n", thread.getName(), num++);
      //如果连续两次调用该方法，则第二次调用将返回 false
      System.out.printf("second value of interrupt is %s", thread
              .isInterrupted() + "");
    }
    System.out.printf("%s is interrupted", thread.getName());
  }
}

class NumberTrackerSleeper implements Runnable {

  @Override
  public void run() {
    Thread thread = Thread.currentThread();
    int num = 0;
    while (!thread.isInterrupted()) {
      System.out.printf("%s  , number is %d\n", thread.getName(), num++);
      try {
        thread.sleep(6000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.printf("%s is interrupted now", thread.getName());
    //如果连续两次调用该方法，则第二次调用将返回 false
    System.out.printf("second value of interrupt is %s", thread
            .isInterrupted()+"");
  }
}
