package concurrent.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * ThreadPoolExecutor类中的invokeAny()方法接收任务数列，并启动它们，返回完成时没有抛出异常的第一个 任务的结果。
 * 该方法返回的数据类型与启动任务的call()方法返回的类型一样。
 */
public class InvokeAny {

  /**
   * 有两个任务，可以返回true值或抛出异常。有以下4种情况：
   * 两个任务都返回ture。invokeAny()方法的结果是第一个完成任务的名称。
   * 第一个任务返回true，第二个任务抛出异常。invokeAny()方法的结果是第一个任务的名称。
   * 第一个任务抛出异常，第二个任务返回true。invokeAny()方法的结果是第二个任务的名称。
   * 两个任务都抛出异常。在本例中，invokeAny()方法抛出一个ExecutionException异常。
   */
  public static void main(String[] args) {
    Collection<Callable<String>> list = new ArrayList<>();
    list.add(new UserValidator());
    list.add(new InvalidUserValidator());

    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    try {
      //只要有一个成功返回就可
      String value = threadPoolExecutor.invokeAny(list);
//      String value = threadPoolExecutor.invokeAll(list);
      System.out.println("the final value retrieved is " + value);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    threadPoolExecutor.shutdown();
  }

}

class UserValidator implements Callable<String> {

  @Override
  public String call() throws Exception {
    TimeUnit.SECONDS.sleep(5);
    return "from UserValidator";
  }
}

class InvalidUserValidator implements Callable<String> {

  @Override
  public String call() throws Exception {
    throw new Exception("exception from InvalidUserValidator");
  }
}
