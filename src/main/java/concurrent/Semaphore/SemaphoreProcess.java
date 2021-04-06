package concurrent.Semaphore;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量对象内部有一个对许可的计数，当这个数大于零时，可以对处理许可并进行执行。当释放时这个计数会加1.
 * 当信号量的许可数够多时，允许继续执行。通常用于多个线程需要对有限资源进行竞争处理上。
 */
public class SemaphoreProcess {

  private Semaphore semaphore = new Semaphore(2);//允许并发两个请求处理的信号量

  //此Queue非阻塞的，如果队列为空时，take()会立即返回null,而不是阻塞等待可用对象
  private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();

  public static void main(String[] args) {
    consume();

  }

  private static void consume() {
    SemaphoreProcess sp = new SemaphoreProcess();
    for (int i = 0; i < 15; i++) {
      sp.add(i + "");
    }

    for (int i = 0; i < 5; i++) {
      Thread thread = new Thread(() -> {
        while (true) {
          String value = sp.getValueBytry();   /**IMPORTANT**/
          System.out.println(Thread.currentThread().getName() + " get value " +
                  "" + value);
          try {
            TimeUnit.SECONDS.sleep(2);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });

      thread.start();
    }
  }

  public void add(String val) {
    queue.offer(val);
  }

  public String getValue() {
    String val = null;
    try {
      System.out.println(Thread.currentThread().getName() + " is waiting");
      semaphore.acquire(); //申请一个许可
      //acquireUninterruptibly 当线程被中断不抛出异常
//      semaphore.acquire(2);//当有2个许可时才允许执行
      TimeUnit.SECONDS.sleep(2);
      System.out.println(Thread.currentThread().getName() + " is leaving");
      val = queue.poll();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
//      semaphore.release(2); //释放2个许可
      return val;
    }
  }

  /**
   * Semaphore类有另2个版本的 acquire() 方法：
   * 1. acquireUninterruptibly()：acquire()方法是当semaphore的内部计数器的值为0时，阻塞线程直到semaphore被释放。
   * 在阻塞期间，线程可能会被中断，然后此方法抛出InterruptedException异常。而此版本的acquire方法会忽略线程的中断而且不会抛出任何异常。
   * <p/>
   * 2. tryAcquire()：此方法会尝试获取semaphore。如果成功，返回true。如果不成功，返回false值，
   * 并不会被阻塞和等待semaphore的释放。接下来是你的任务用返回的值执行正确的行动。
   */
  public String getValueBytry() {
    String val = null;
    boolean isValid = true;
    try {
      System.out.println(Thread.currentThread().getName() + " is waiting in " +
              "method getValueBytry()");
      isValid = semaphore.tryAcquire(); //申请一个许可,当没有许可时，不会阻塞
      if (!isValid) {
        return "NA";
      }
      TimeUnit.SECONDS.sleep(2);
      System.out.println(Thread.currentThread().getName() + " is leaving in " +
              "getValueBytry()");
      val = queue.poll();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (isValid) {
        semaphore.release();
      }
      return val;
    }
  }


}
