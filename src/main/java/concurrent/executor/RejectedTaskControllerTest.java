package concurrent.executor;


import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectedTaskControllerTest implements
        RejectedExecutionHandler {
  public static void main(String[] args) {
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    threadPoolExecutor.setRejectedExecutionHandler(new RejectedTaskControllerTest());

    threadPoolExecutor.submit(() -> {
      TimeUnit.SECONDS.sleep(5);
      return "hello";
    });

    /**
     * 当执行者接收任务时，会检查shutdown()是否已经被调用了。如果被调用了，它拒绝这个任务。
     * 首先，它查找 setRejectedExecutionHandler()设置的处理器。如果有一个（处理器），
     * 它调用那个类的 rejectedExecution()方法，否则，它将抛RejectedExecutionExeption异常。
     * 这是一个运行时异常，所以你不需要 用catch语句来控制它。
     */
    threadPoolExecutor.shutdown();

    //再次提交后被拒绝
    threadPoolExecutor.submit(() -> {
      return "yes";
    });

    try {
      threadPoolExecutor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    System.out.printf("RejectedTaskController: The task %s has been rejected\n", r.toString());
    System.out.printf("RejectedTaskController: %s\n", executor.toString());
    System.out.printf("RejectedTaskController: Terminating:%s\n", executor.isTerminating());
    System.out.printf("RejectedTaksController: Terminated:%s\n", executor.isTerminated());
  }
}


