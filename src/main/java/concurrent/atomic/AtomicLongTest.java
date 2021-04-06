package concurrent.atomic;
//
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

  AtomicLong count;

  //  @Before
  public void setUp() {
    count = new AtomicLong();
  }

  //  @Test
  public void basicAddTest() {
    assertEquals(count.getAndIncrement(), 0);  //1
    assertEquals(count.get(), 1);
    assertEquals(count.getAndAdd(2), 1);  //3
    assertEquals(count.get(), 3);
    assertEquals(count.addAndGet(1), 4);//4

    assertEquals(count.getAndUpdate(longVal -> {
      return 5;
    }), 4);
    assertEquals(count.get(), 5);
    assertEquals(count.updateAndGet(longVal -> {
      return 5;
    }), 5);

    count.lazySet(44);
    assertEquals(count.get(), 44);
  }

  //  @Test
  public void basicDecreaseTest() {
    assertEquals(count.getAndAdd(44), 0);  //1
    assertEquals(count.get(), 44);//44
    assertEquals(count.getAndDecrement(), 44);  //3
    assertEquals(count.get(), 43);
    assertEquals(count.decrementAndGet(), 42);//4
    assertEquals(42, count.getAndAccumulate(1, (prev, longVal) -> {
      System.out.printf("%d-%d", prev, longVal);
      return prev + longVal;
    }));

    assertEquals(43, count.get());
  }

  void assertEquals(Object val1, Object val2) {
    if (!val1.equals(val2)) {
      throw new RuntimeException("not equal.");
    }
  }
}
