package concurrent.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * 允许2个并发任务间相互交换数据的同步应用。更具体的说，Exchanger 类允许在2个线程间定义同步点，
 * 当2个线程到达这个点，他们相互交换数据类型，使用第一个线程的数据类型变成第二个的，然后第二个线程的数据类型变成第一个的。
 */
public class ProducerConsumerTest {
  public static void main(String[] args) {
    Exchanger<List<String>> exchanger = new Exchanger<>();
    Producer producer = new Producer(exchanger);
    Consumer consumer = new Consumer(exchanger);

    producer.start();
    consumer.start();

  }

  static class Producer extends Thread {
    Exchanger<List<String>> exchanger;

    public Producer(Exchanger<List<String>> exchanger) {
      this.exchanger = exchanger;
    }

    public void run() {

      List<String> list;
      list = generateList();

      int i = 0;
      while (true) {
        try {

          if (i == 3) {
            return;
          }
          List<String> list2 = this.exchanger.exchange(list);

          i++;
          TimeUnit.SECONDS.sleep(3);
        /*System.out.println("===Produce : get data from consumer ===");
        list2.forEach(val -> System.out.print(val + ","));
        System.out.println("================== END ===================");*/
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    private List<String> generateList() {
      List<String> list = new ArrayList<>();
      Random random = new Random();
      for (int i = 0; i < 10; i++) {
        list.add("val-" + random.nextInt(200));
      }
      return list;
    }
  }

  static class Consumer extends Thread {
    Exchanger<List<String>> exchanger;

    public Consumer(Exchanger<List<String>> exchanger) {
      this.exchanger = exchanger;
    }

    public void run() {
      List<String> list = new ArrayList<>();
      Random random = new Random();

      while (true) {
        try {
          list = this.exchanger.exchange(list);
          System.out.println("===Consumer : get data from consumer ===");
          list.forEach(val -> System.out.print(val + ","));
          System.out.println("\n================== END ===================");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

}


