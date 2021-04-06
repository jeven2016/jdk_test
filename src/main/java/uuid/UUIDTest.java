package uuid;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * JUG 第一次初始化消耗比JDK实现要慢一倍，但后续生成UUID的效率是JDK实现的6倍
 */
public class UUIDTest {
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 15; i++)
      run();
  }

  private static void run() throws InterruptedException {
    Thread.currentThread().sleep(2000);

    long timer1 = System.currentTimeMillis();
    for (int i = 0; i < 10000; i++) {
      getByJug();
    }
    long timer2 = System.currentTimeMillis();

    Thread.currentThread().sleep(2000);
    long timer3 = System.currentTimeMillis();
    for (int i = 0; i < 10000; i++) {
      getByJdk();
    }
    long timer4 = System.currentTimeMillis();

    System.out.println("getByJug:" + (timer2 - timer1) + " ms");
    System.out.println("getByJdk:" + (timer4 - timer3) + " ms");
  }

  public static String getByJug() {
    UUID uuid = Generators.timeBasedGenerator().generate();
    return uuid.toString();
  }

  public static String getByJdk() {
    return UUID.randomUUID().toString();
  }
}
