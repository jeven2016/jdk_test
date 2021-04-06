package concurrent.fork_join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 统计1到20的和
 */
public class RecursiveActionTest {

  public int sum;
  private List<Integer> list = new ArrayList<>();

  public RecursiveActionTest() {
    for (int i = 0; i < 20; i++) {
      sum += i;
      list.add(i);
    }
  }

  public static void main(String[] args) {
    RecursiveActionTest test = new RecursiveActionTest();
    System.out.println("the expect sum is " + test.sum);
    ForkJoinPool pool = new ForkJoinPool();
    pool.execute(new PrintTask(test.list, 0, test.list.size()));

    pool.shutdown();
    try {
      pool.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

/**
 * 分两个子任务分别打印[0..10), [10..20)的数字
 * <p>
 * RecursiveAction里面的compute没有返回值，但是RecursiveTask会有返回值
 */
class PrintTask extends RecursiveAction {
  /**
   * 声明类的序列版本UID。这个元素是必需的，因为RecursiveAction类的父类ForkJoinTask实现了Serializable接口。
   */
  private static final long serialVersionUID = 1L;
  private List<Integer> list;
  private int begin;
  private int end;

  public PrintTask(List<Integer> list, int begin, int end) {
    this.list = list;
    this.begin = begin;
    this.end = end;
  }

  @Override
  protected void compute() {
    if (end - begin > 10) {
      //分两个子任务处理
      PrintTask pt = new PrintTask(list, 0, 10);
      PrintTask pt2 = new PrintTask(list, 10, list.size());

      //执行2个子任务
      /**
       * 它调用invokeAll()方法，执行每个任务所创建的子任务。这是一个同步调用，
       * 这个任务在继续（可能完成）它的执行之前，必须等待子任务的结束。当任务正在等待它的子任务（结束）时，
       * 正在执行它的工作线程执行其他正在等待的任务。在这种行为下，
       * Fork/Join框架比Runnable和Callable对象本身提供一种更高效的任务管理。
       */
      invokeAll(pt, pt2);
    } else {
      //否则在此任务中处理
      try {
        int sum = IntStream.range(begin, end).sum();
        System.out.println("sum([" + begin + "," + end + ")=" + sum);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }
}
