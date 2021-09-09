package concurrent.lock;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我们通过一个实际的例子来解释Condition的用法：
 *
 * <p>我们要打印1到9这9个数字，由A线程先打印1，2，3，然后由B线程打印4,5,6，然后再由A线程打印7，8，9. 这道题有很多种解法，现在我们使用Condition来做这道题
 *
 * 所有Condition对象都与锁有关，并且使用声明在Lock接口中的newCondition()方法来创建。使用condition做任何操作之前，
 * 你必须获取与这个condition相关的锁的控制。 所以，condition的操作一定是在以调用Lock对象的lock()方法为开头，以调用相同
 * Lock对象的unlock()方法为结尾的代码块中。
 * 当一个线程在一个condition上调用await()方法时，它将自动释放锁的控制，所以其他线程可以获取这个锁的控制并开始执行相同操作，或者由同个锁保护的其他临界区。
 * 注释：当一个线程在一个condition上调用signal()或signallAll()方法，一个或者全部在这个condition上等待的线程将被唤醒。这并不能保证的使它们现在睡眠的条件现在是true，
 * 所以你必须在while循环内部调用await()方法。你不能离开这个循环，直到 condition为true。当condition为false，你必须再次调用 await()方法。
 * 你必须十分小心
 * ，在使用await()和signal()方法时。如果你在condition上调用await()方法而却没有在这个condition上调用signal()方法，这个线程将永远睡眠下去。
 * 在调用await()方法后，一个线程可以被中断的，所以当它正在睡眠时，你必须处理InterruptedException异常。
 */
public class LockCondition {

  private ReentrantLock lock = new ReentrantLock();

  private Condition fullCondition = lock.newCondition(); //队列已满的条件，不能再添加  ->>
  // add
  private Condition consumCondition = lock.newCondition(); //队列不为空可以执行处理的条件
  // -》get

  private LinkedList<String> strList = new LinkedList();
  private int maxNumber = 20;

  public static void main(String[] args) {
    LockCondition lockCondition = new LockCondition();

    for (int i = 0; i < 3; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          int i = 0;
          while (true) {
            if (i > 20) {
              i = 0;
            }
            lockCondition.add(Thread.currentThread().getName() + "-" + i++);
          }
        }
      });
      thread.start();
    }

    for (int i = 0; i < 2; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            System.out.println(Thread.currentThread().getName() + "-get-" +
                    lockCondition.getNext());
          }
        }
      });
      thread.start();
    }

    try {
      TimeUnit.MINUTES.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void add(String str) {
    try {
      //先锁住
      lock.lock();

      //如果队列已满，则当前现在符合
      while (strList.size() > 20) {
        System.out.println(Thread.currentThread().getName() + " : the list is " +
                "full.");
        fullCondition.await();
      }

      strList.add(str);

      //唤醒不为空的线程进行处理
      consumCondition.signalAll();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public String getNext() {
    String val = null;
    try {
      lock.lock();

      while (strList.isEmpty()) {
        System.out.println(Thread.currentThread().getName() + " : the list is " +
                "empty and this thread is in awaiting.");
        consumCondition.await();
      }
      val = strList.poll();
      fullCondition.signalAll();//唤醒队列满的条件
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return val;
  }

}
