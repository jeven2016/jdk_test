package concurrent;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 这种线程的优先级非常低，通常在程序里没有其他线程运行时才会执行它。当守护线程是程序里唯一在运行的线程时，JVM会结束守护线程并终止程序。
 * 根据这些特点，守护线程通常用于在同一程序里给普通线程（也叫使用者线程）提供服务。它们通常无限循环的等待服务请求或执行线程任务。
 * 它们不能做重要的任务，因为我们不知道什么时候会被分配到CPU时间片，并且只要没有其他线程在运行，它们可能随时被终止。
 * JAVA中最典型的这种类型代表就是垃圾回收器。
 */
public class DaemonThreadTest {


  public static void main(String[] args) {
    ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(100000);
    for (int i = 0; i < 10; i++) {
      queue.offer(new Random().nextInt() + "Val");
    }

    QueueComsumer comsumer = new QueueComsumer(queue);
    Thread producer = new Thread(new QueueProducer(queue));

    comsumer.start();
    producer.start();

    try {
      comsumer.join();
      producer.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }


}


class QueueComsumer extends Thread {
  ArrayBlockingQueue<String> queue;

  public QueueComsumer(ArrayBlockingQueue<String> queue) {
    this.queue = queue;
    setDaemon(true);
  }

  @Override
  public void run() {
    String val;
    try {
      //获取字符串并处理
      while ((val = queue.take()) != null) {
        System.out.println("after consumer ,size=" + queue.size());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class QueueProducer implements Runnable {
  ArrayBlockingQueue<String> queue;

  public QueueProducer(ArrayBlockingQueue<String> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    String val;
    try {
      while (true) {
        //获取字符串并处理
        queue.offer(new Random().nextInt() + "Val");
        queue.offer(new Random().nextInt() + "Val");
        queue.offer(new Random().nextInt() + "Val");
        queue.offer(new Random().nextInt() + "Val");
        queue.offer(new Random().nextInt() + "Val");
        queue.offer(new Random().nextInt() + "Val");
        System.out.println("after produce size=" + queue.size());
        TimeUnit.SECONDS.sleep(2);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

