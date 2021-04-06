package concurrent.executor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CompletionService 类有一个方法来提交任务给执行者和另一个方法来获取已完成执行的下个
 * 任务的Future对象。在内部实现中，它使用Executor对象执行任务。这种行为的优点是共享一个
 * CompletionService对象，并提交任务给执行者，这样其他（对象）可以处理结果。其局限性是，
 * 第二个对象只能获取那些已经完成它们的执行的任务的Future对象，所以，这些Future对象只能获取任务的结果。
 * <p>
 * 使用提供的 Executor 来执行任务的 CompletionService。此类将安排那些完成时提交的任务，
 * 把它们放置在可使用 take 访问的队列上。该类非常轻便，适合于在执行几组任务时临时使用。
 *
 * 这个类同样提供其他两个方法，用来获取已完成任务的Future对象。这两个方法如下：

 * poll()：不带参数版本的poll()方法，检查是否有任何Future对象在队列中。如果列队是空的，
 * 它立即返回null。否则，它返回第一个元素，并从列队中删除它。
 * take()：这个方法，不带参数。检查是否有任何Future对象在队列中。如果队列是空的，它阻塞
 * 线程直到队列有一个元素。当队列有元素，它返回第一元素，并从列队中删除它。

 *
 * 作用：将任务的启动和处理分离开处理
 */
public class CompletionServiceTest {

  public static void main(String[] args) {

    //将经过线程池获得的结果保存在内部队列中
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    ExecutorCompletionService<String> ecs = new
            ExecutorCompletionService<String>(threadPoolExecutor);

    //生产数据的线程
    Thread threadGenerator = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        try {
          //随即等待数秒后添加执行Callable
          int seconds = new Random().nextInt(4);
          TimeUnit.SECONDS.sleep(seconds);
          ecs.submit(() -> "\"" + Thread.currentThread().getName() + " wait for" +
                  " " + seconds + "\"");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      //关闭
      threadPoolExecutor.shutdown();
      try {
        threadPoolExecutor.awaitTermination(1,TimeUnit.SECONDS);
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(Thread.activeCount());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    //处理线程
    Thread thread2 = new Thread(() -> {
      try {
        String val;
        while ((val = ecs.take().get()) != null) {
          System.out.println("===" + Thread.currentThread().getName() + " get " +
                  "value " +
                  val);
        }

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    });

    threadGenerator.start();
    thread2.start();

  }
}
