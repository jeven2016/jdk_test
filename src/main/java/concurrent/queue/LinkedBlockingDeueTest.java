package concurrent.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * LinkedBlockingDeque类同时提供方法用于添加和获取列表的元素，而不被阻塞，或抛出异常，或返回null值。这些方法是：
 * <p/>
 * takeFirst() 和takeLast()：这些方法分别返回列表的第一个和最后一个元素。它们从列表删除返回的元素。如果列表为空，这些方法将阻塞线程，直到列表有元素。
 * getFirst() 和getLast()：这些方法分别返回列表的第一个和最后一个元素。它们不会从列表删除返回的元素。如果列表为空，这些方法将抛出NoSuchElementExcpetion异常。
 * peek()、peekFirst(),和peekLast()：这些方法分别返回列表的第一个和最后一个元素。它们不会从列表删除返回的元素。如果列表为空，这些方法将返回null值。
 * poll()、pollFirst()和 pollLast()：这些方法分别返回列表的第一个和最后一个元素。它们从列表删除返回的元素。如果列表为空，这些方法将返回null值。
 * add()、 addFirst()、addLast()：这些方法分别在第一个位置和最后一个位置上添加元素。如果列表已满（你已使用固定大小创建它），这些方法将抛出IllegalStateException异常。
 */
public class LinkedBlockingDeueTest {
  public static void main(String[] args) {

    LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<>(20);

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    Object obj = new Object();

    //添加一个不停添加数据的线程
    executor.submit(() -> {
      //创建数据
      for (int i = 0; i < 30; i++) {
        String value = "val_" + i;
        boolean result = queue.offer(value);
        System.out.println("--" + Thread.currentThread().getName() + " insert result " + result);
        try {
          if (i % 10 == 0) {
            TimeUnit.SECONDS.sleep(2);
            /*synchronized (obj) {
              obj.wait(2000);
            }*/
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    //添加一个不停消费的线程
    executor.submit(() -> {
      try {
        String value;

        //poll,peek不会阻塞等待
        while ((value = queue.poll()) != null) {
          System.out.println("** " + Thread.currentThread().getName() + " peek data " + value);
//          obj.notifyAll();
        }

        //take 会阻塞等待
        // System.out.println("** " + Thread.currentThread().getName() + " peek data " + queue.peek());
        while ((value = queue.take()) != null) {
          System.out.println("** " + Thread.currentThread().getName() + " consume data " + value);
//          obj.notifyAll();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

   /* executor.shutdown();
    try {
      executor.awaitTermination(1,TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/


  }


}
