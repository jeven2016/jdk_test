package concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;
import lombok.extern.slf4j.Slf4j;

/** 它不再像compareAndSet方法 中只比较内存中的值也当前值是否相等，而且先比较引用是否相等，然后比较值是否相等，这样就避免了ABA问题。 */
@Slf4j
public class AtomicStampedReferenceTest {

  public static void main(String... args) throws InterruptedException {
    new AtomicStampedReferenceTest().run();
  }

  /**
   * two threads firstly get the stamp (value 1) and then thread-1 update, at last thread-2 failed
   * to update with old stamp value
   *
   * @throws InterruptedException
   */
  private static void run() throws InterruptedException {
    var cd = new CountDownLatch(2);
    AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(100, 1);

    // thread-1
    var thread1 =
        new Thread(
            () -> {
              var version1 = ref.getStamp();

              try {
                // wait for 3 seconds and make thread-2 to get this stamp value
                TimeUnit.SECONDS.sleep(3);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              var result = ref.compareAndSet(100, 101, version1, 2);
              log.info("{} : {}", "thread-1", result);
              cd.countDown();
            },
            "thread-1");

    // thread-2
    var thread2 =
        new Thread(
            () -> {
              var version1 = ref.getStamp();

              try {
                // wait for 5 seconds and make thread-1 complete
                TimeUnit.SECONDS.sleep(5);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              var result = ref.compareAndSet(100, 102, version1, 2);
              log.info("{} : {}", "thread-2", result);
              cd.countDown();
            },
            "thread-2");

    thread1.run();
    thread2.run();
    cd.await();

    System.out.println("finished");
  }
}
