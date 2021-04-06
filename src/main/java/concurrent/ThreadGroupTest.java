package concurrent;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Java并发 API里有个有趣的方法是把线程分组。这个方法允许我们按线程组作为一个单位来处理。例如，你有一些线程做着同样的任务，
 * 你想控制他们，无论多少线程还在运行，他们的状态会被一个call 中断。
 * Java 提供 ThreadGroup 类来组织线程。 ThreadGroup 对象可以由 Thread 对象组成和由另外的 ThreadGroup 对象组成,生成线程树结构。
 * 在这个指南中, 我们将开发一个简单的例子来学习 ThreadGroup 对象。我们有 10 个随机时间休眠的线程 (例如，模拟搜索)，
 * 然后当其中一个完成，就中断其余的。
 */
public class ThreadGroupTest {
  public static void main(String[] args) {

    ThreadGroup threadGroup = new ThreadGroup("Searcher");
    Result result = new Result();
    SearchTask searchTask = new SearchTask(result);

    for (int i = 0; i < 5; i++) {
      Thread thread = new Thread(threadGroup, searchTask);
      thread.start();
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
    System.out.printf("Information about the Thread Group\n");
    threadGroup.list();

    Thread[] threads = new Thread[threadGroup.activeCount()];
    threadGroup.enumerate(threads);
    for (int i = 0; i < threadGroup.activeCount(); i++) {
      System.out.printf("Thread %s: %s\n", threads[i].getName(), threads[i].getState());
    }

    waitFinish(threadGroup);
    threadGroup.interrupt();
  }

  private static void waitFinish(ThreadGroup threadGroup) {
    while (threadGroup.activeCount() > 9) {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}


class SearchTask implements Runnable {

  private Result result;

  public SearchTask(Result result) {
    this.result = result;
  }


  @Override
  public void run() {
    String name = Thread.currentThread().getName();
    System.out.printf("Thread %s: Start\n", name);
    try {
      doTask();
      result.setName(name);
    } catch (InterruptedException e) {
      System.out.printf("Thread %s: Interrupted\n", name);
      return;
    }
    System.out.printf("Thread %s: End\n", name);
  }


  private void doTask() throws InterruptedException {
    Random random = new Random((new Date()).getTime());
    int value = (int) (random.nextDouble() * 100);
    System.out.printf("Thread %s: %d\n", Thread.currentThread().getName(), value);
    TimeUnit.SECONDS.sleep(value);
  }


}

class Result {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}