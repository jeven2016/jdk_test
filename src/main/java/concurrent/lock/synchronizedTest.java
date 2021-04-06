package concurrent.lock;


import java.util.concurrent.TimeUnit;

public class synchronizedTest {
  public static void main(String[] args) {
    synchronizedTest st = new synchronizedTest();

//    testSynCall(st);

//    callStaticMethod(st);

    lockStaticAndInstantMethod(st);
  }

  /**
   * 一个线程锁住静态方法，不会阻塞另外一个调用实例的同步方法
   * @param st
   */
  private static void lockStaticAndInstantMethod(final synchronizedTest st) {
    Thread thr1 = new Thread() {
      @Override
      public void run() {
        st.a();
      }
    };

    Thread thr2 = new Thread() {
      @Override
      public void run() {
        synchronizedTest.staticMethod();
      }
    };

    thr2.start();
    thr1.start();

    try {
      thr2.join();
      thr1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 静态方法调用的线程先占用了锁，造成实例方法对同方法的实例调用被阻塞
   */
  private static void callStaticMethod(final synchronizedTest st) {
    Thread thr1 = new Thread() {
      @Override
      public void run() {
        st.callStaticMethod();
      }
    };

    Thread thr2 = new Thread() {
      @Override
      public void run() {
        synchronizedTest.staticMethod();
      }
    };

    thr2.start();
    thr1.start();

    try {
      thr2.join();
      thr1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void testSynCall(final synchronizedTest st) {
    Thread thr1 = new Thread() {
      @Override
      public void run() {
        st.a();
      }
    };

    Thread thr2 = new Thread() {
      @Override
      public void run() {
        st.b();
      }
    };

    thr1.start();
    thr2.start();

    try {
      thr1.join();
      thr2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    /**
     * 将会打印
     * Thread-0 enter a.....
     Thread-0 enter b.....
     Thread-1 enter b.....
     */}

  /**
   * 在静态方法上加同步锁，针对class锁定
   */
  public static synchronized void staticMethod() {
    System.out.println(Thread.currentThread().getName() + " enter staticMethod.....");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 该方法先锁住对象的a方法，并随后进入b方法
   */
  public synchronized void a() {
    System.out.println(Thread.currentThread().getName() + " enter a.....");
    try {
      TimeUnit.SECONDS.sleep(5);
      b();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public synchronized void b() {
    System.out.println(Thread.currentThread().getName() + " enter b.....");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void callStaticMethod() {
    System.out.println(Thread.currentThread().getName() + " enter callStaticMethod.....");
    try {
      staticMethod();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
