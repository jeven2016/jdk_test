package concurrent.executor;

import java.util.List;
import java.util.concurrent.*;

/**
 * 从Java5开始JDK并发API提供一种机制。这个机制被称为Executor framework，接口核心是Executor，Executor的子接口是ExecutorService，
 * 而ThreadPoolExecutor类则实现了这两个接口。
 * 这个机制将任务的创建与执行分离。使用执行者，你只要实现Runnable对象并将它们提交给执行者。执行者负责执行，实例化和运行这些线程。
 * 除了这些，它还可以使用线程池提高了性能。当你提交一个任务给这个执行者，它试图使用线程池中的线程来执行任务，从而避免继续创建线程。
 * <p/>
 * Callable接口是Executor framework的另一个重要优点。它跟Runnable接口很相似，但它提供了两种改进，如下：
 * <p/>
 * 这个接口中主要的方法叫call()，可以返回结果。
 * 当你提交Callable对象到执行者，你可以获取一个实现Future接口的对象，你可以用这个对象来控制Callable对象的状态和结果
 */
public class FirstThreadPool {

  public static void main(String[] args) throws InterruptedException {
//    runCachedThreadPool();

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    Callable<String> callable = new Callable<String>() {
      @Override
      public String call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return Thread.currentThread().getName() + " is over.";
      }
    };

    Callable<String> callable2 = new Callable<String>() {
      @Override
      public String call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return Thread.currentThread().getName() + " is over.";
      }
    };

    Future<String> future1 = executorService.submit(callable);
    Future<String> future2 = executorService.submit(callable2);

    try {
      String value1 = future1.get();
      System.out.println("get value from future1 =" + value1);
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    try {
      String value2 = future2.get();
      System.out.println("get value from future1 =" + value2);
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    if (future1.isDone()) {
      future1 = executorService.submit(callable);

      try {
        //设置超时
        String value1 = future1.get(7,TimeUnit.SECONDS);
        System.out.println("get value again from future1 =" + value1);

        //已经调用后，再调用
        value1 =  future2.get();
        System.out.println("get value from future1 through got before=" + value1);

        //已经调用后，再调用
        value1 =  future2.get();
        System.out.println("get value from future1 through got before=" + value1);

        //已经调用后，再调用
        value1 =  future2.get();
        System.out.println("get value from future1 through got before=" + value1);
      } catch (ExecutionException e) {
        e.printStackTrace();
      } catch (TimeoutException e) {
        e.printStackTrace();
      }
    }
  }

  private static void runCachedThreadPool() throws InterruptedException {
    /**
     * Executors类的 newCachedThreadPool()方法创建的基本ThreadPoolExecutor，你会有执行者运行在某一时刻的线程数的问题。
     * 这个执行者为每个接收到的任务创建一个线程（如果池中没有空闲的线程），所以，如果你提交大量的任务，并且它们有很长的（执行）时间，
     * 你会使系统过载和引发应用程序性能不佳的问题。

     如果你想要避免这个问题，Executors类提供一个方法来创建大小固定的线程执行者。这个执行者有最大线程数。 如果你提交超过这个最大线程数的任务，
     这个执行者将不会创建额外的线程，并且剩下的任务将会阻塞，直到执行者有空闲线程。这种行为，保证执行者不会引发应用程序性能不佳的问题。
     */
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//    Executors.newFixedThreadPool(3);

    /**
     * Executors类同时提供newSingleThreadExecutor()方法。这是大小固定的线程执行者的一个极端例子。它创建只有一个线程的执行者，
     * 所以它在任意时刻只能执行一个任务。
     */
//    Executors.newSingleThreadExecutor();

    Runnable thrd = new Runnable() {
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(3);
          System.out.println(Thread.currentThread().getName() + " print");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Runnable thrd2 = new Runnable() {
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(2);
          System.out.println(Thread.currentThread().getName() + " print");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Runnable thrd3 = new Runnable() {
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(1);
          System.out.println(Thread.currentThread().getName() + " print");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Runnable thrd4 = new Runnable() {
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(2);
          System.out.println(Thread.currentThread().getName() + " print");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    threadPoolExecutor.execute(thrd);
    threadPoolExecutor.execute(thrd2);
    threadPoolExecutor.execute(thrd3);
    threadPoolExecutor.execute(thrd3);

    System.out.println("active count=" + threadPoolExecutor.getActiveCount());
    System.out.println("completed task count=" + threadPoolExecutor.getCompletedTaskCount());
    System.out.println("getCorePoolSize=" + threadPoolExecutor.getCorePoolSize());
    System.out.println("getPoolSize=" + threadPoolExecutor.getPoolSize());
    System.out.println("getTaskCount=" + threadPoolExecutor.getTaskCount());
    System.out.println("getKeepAliveTime=" + threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));

    TimeUnit.SECONDS.sleep(5);
    List<Runnable> list = threadPoolExecutor.shutdownNow();
    threadPoolExecutor.shutdown();
  }

}
