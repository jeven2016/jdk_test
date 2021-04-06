package concurrent.executor;

import java.util.concurrent.*;

public class ScheduleThreadTest {
  public static void main(String[] args) {
    ScheduledExecutorService poolExecutor = (ScheduledExecutorService) Executors.newScheduledThreadPool(3);
    delayExecute(poolExecutor);
    scheduleExecute(poolExecutor);


//    poolExecutor.shutdown();
   /* try {
      poolExecutor.awaitTermination(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/

  }

  private static void scheduleExecute(ScheduledExecutorService poolExecutor) {
    System.out.println("***************周期2秒执行 **************");
    //延后2秒执行,并且每隔2秒执行
    poolExecutor.scheduleAtFixedRate(() -> {
      String threadName = Thread.currentThread().getName();
      System.out.println(threadName + " run with 2 seconds period.");

    }, 2, 2, TimeUnit.SECONDS);

  }

  private static void delayExecute(ScheduledExecutorService poolExecutor) {
    System.out.println("***************延后5秒执行 **************");
    //延后5秒执行
    ScheduledFuture<String> future = poolExecutor.schedule(() -> {
      String threadName = Thread.currentThread().getName();
      System.out.println(threadName + " begin to run.");
      return threadName + "-wang";
    }, 2, TimeUnit.SECONDS);

    try {
      String value = future.get(4, TimeUnit.SECONDS);
      System.out.println(Thread.currentThread().getName() + " return a value is" +
              value);

      System.out.println("value is done =" + future.isDone());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
    }

    //取消这个任务
//    future.cancel(true);
  }
}
