package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * CountDownLatch类有3个基本元素：
 * 1. 初始值决定CountDownLatch类需要等待的事件的数量。
 * 2. await() 方法, 被等待全部事件终结的线程调用。
 * 3. countDown() 方法，事件在结束执行后调用。
 */
public class CountDownLatchTest {

  List<String> members = new ArrayList<>();
  //当线程运行时需要等待的数量（需要5个）
  private CountDownLatch menbersCtrl = new CountDownLatch(5);

  public static void main(String[] args) {
    CountDownLatchTest ct = new CountDownLatchTest();
    waitAllMembersToWork(ct);


  }

  private static void waitAllMembersToWork(CountDownLatchTest ct) {
    //启动5个线程模拟人员加入
    List<Thread> thrdList = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      Thread thr = new Thread(() -> {
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        ct.arrive(Thread.currentThread().getName());
      });
      thrdList.add(thr);
      thr.start();
    }

    Thread monitorThr = new Thread(() -> {
      ct.countAllMembers();
    });
    monitorThr.start();

    /*try {
      TimeUnit.SECONDS.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
  }

  public void arrive(String name) {
    if (menbersCtrl.getCount() <= 0) {
      System.out.println(name + " is reject ");
      return;
    }
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    menbersCtrl.countDown();
    System.out.println(name + " arrives and waiting for " + menbersCtrl
            .getCount() + " member(s)");
    members.add(name);
  }

  public void countAllMembers() {
    try {
      //等待所有members
      menbersCtrl.await();

      System.out.println("============================");
      members.stream().forEach(name -> {
        System.out.println(name + " is arrived.");
      });
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
