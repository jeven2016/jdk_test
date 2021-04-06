package concurrent.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 关于invokeAll()方法，你会使用Future对象获取任务的结果。当所有的任务完成时，这个方法结束，如果你调用返回的Future对象的isDone()方法，
 * 所有调用都将返回true值。
 */
public class ThreadPoolShutdown {

  public static void main(String[] args) {
    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

//    invokeAllAndShutdown(executorService);
    shutdownAndSubmit(executorService);

  }

  private static void shutdownAndSubmit(ThreadPoolExecutor executorService) {
    Callable<String> callable = new Callable<String>() {
      @Override
      public String call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        String val = Thread.currentThread().getName() + " is over.";
        System.out.println(val);
        return val;
      }
    };

    Callable<String> callable2 = new Callable<String>() {
      @Override
      public String call() throws Exception {
        TimeUnit.SECONDS.sleep(15);
        String val = Thread.currentThread().getName() + " is over.";
        System.out.println(val);
        return val;
      }
    };

    executorService.submit(callable);
    executorService.submit(callable2);

    /**
     *
     * ThreadPoolExecutor类的awaitTermination()方法使线程进入睡眠，直到每一个任务调用shutdown()方法之后完成执行。
     */
    /**使用shutdown()方法关闭执行者
     (按过去执行已提交任务的顺序发起一个有序的关闭，但是不接受新任务。如果线程正在运行不会立即中断，因为调用的是interrupt方法)*/
    executorService.shutdown();
    try {
      //这里如果不加上shutdown,则executor 不会退出
      executorService.awaitTermination(2, TimeUnit.SECONDS);  //执行者的awaitTermination()方法，等待所有任务完成
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void invokeAllAndShutdown(ThreadPoolExecutor executorService) {
    Callable<String> callable = new Callable<String>() {
      @Override
      public String call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return Thread.currentThread().getName() + " is over.";
      }
    };

    List<Callable<String>> list = new ArrayList<>();
    list.add(callable);
    try {
      List<Future<String>> futureList = executorService.invokeAll(list);
      futureList.forEach((future) -> {
        String value = null;
        try {
          value = future.get();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
        System.out.println("the final value is " + value);
      });

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //当所有任务都执行完毕后，关闭线程池
    executorService.shutdown();
  }
}
