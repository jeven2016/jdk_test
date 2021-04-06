package concurrent.executor;

import java.util.concurrent.*;

/**
 * 可取消的异步计算。利用开始和取消计算的方法、查询计算是否完成的方法和获取计算结果的方法，
 * 此类提供了对 Future 的基本实现。仅在计算完成时才能获取结果；如果计算尚未完成，则阻塞 get 方法。
 * 一旦计算完成，就不能再重新开始或取消计算。
 * <p>
 */
public class FutureTest {

  public static void main(String[] args) {
    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    //立即执行
    RuningTaskListener listener = new RuningTaskListener(new CallableProcessor());
    executorService.submit(listener);

    //延后4秒执行
    RuningTaskListener listener2 = new RuningTaskListener(new
            WaitingProcessor());
    executorService.submit(listener);

    executorService.submit(listener);
    executorService.submit(listener2);

    System.out.println("************测试done*****************");
    System.out.println("listener is done =" + listener.isDone());
    System.out.println("listener2 is done = " + listener2.isDone());

    try {
      TimeUnit.SECONDS.sleep(2);
      listener.cancel(true);
      listener2.cancel(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  static class RuningTaskListener extends FutureTask<String> {

    public RuningTaskListener(Callable<String> callable) {
      super(callable);
    }

    /**
     * FutureTask类提供一个done()方法，允许你在执行者执行任务完成后执行一些代码。你可以用来做一些后处理操作，
     * 生成一个报告，通过e-mail发送结果，或释放一些资源。当执行的任务由FutureTask来控制完成，
     * FutureTask会内部调用这个方法。这个方法在任务的结果设置和它的状态变成isDone状态之后被调用，
     * 不管任务是否已经被取消或正常完成。
     */
    @Override
    protected void done() {
      System.out.println(Thread.currentThread().getName() + " print over " +
              "after " +
              "it is done");
    }
  }

  /**
   * 立即执行
   */
  static class CallableProcessor implements Callable<String> {
    @Override
    public String call() throws Exception {
      System.out.println(Thread.currentThread().getName() + "running in CallableProcessor.");
      return "value from CallableProcessor";
    }
  }

  /**
   * 需要等待3秒
   */
  static class WaitingProcessor implements Callable<String> {
    @Override
    public String call() throws Exception {
      TimeUnit.SECONDS.sleep(4);
      System.out.println(Thread.currentThread().getName() + "running in WaitingProcessor.");
      return "value from WaitingProcessor";
    }
  }
}
