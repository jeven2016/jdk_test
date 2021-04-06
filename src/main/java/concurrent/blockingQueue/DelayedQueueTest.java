package concurrent.blockingQueue;

import java.util.concurrent.DelayQueue;

/**
 * 使用线程安全的、带有延迟元素的列表
 * DelayedQueue类是Java API提供的一种有趣的数据结构，并且你可以用在并发应用程序中。
 * 在这个类中，你可以存储带有激活日期的元素。方法返回或抽取队列的元素将忽略未到期的数据元素。
 * 它们对这些方法来说是看不见的。
 * 为了获取这种行为，你想要存储到DelayedQueue类中的元素必须实现Delayed接口。
 * 这个接口允许你处理延迟对象，所以你将实现存储在DelayedQueue对象的激活日期，
 * 这个激活时期将作为对象的剩余时间，直到激活日期到来。这个接口强制实现以下两种方法：
 * <p>
 * compareTo(Delayed o)：Delayed接口继承Comparable接口。如果执行这个方法的对象的延期
 * 小于作为参数传入的对象时，该方法返回一个小于0的值。如果执行这个方法的对象的延期大于作为
 * 参数传入的对象时，该方法返回一个大于0的值。如果这两个对象有相同的延期，该方法返回0。
 * getDelay(TimeUnit unit)：该方法返回与此对象相关的剩余延迟时间，以给定的时间单位表示。
 * TimeUnit类是一个枚举类，有以下常量：DAYS、HOURS、 MICROSECONDS、MILLISECONDS、 MINUTES、 NANOSECONDS 和 SECONDS。
 */
public class DelayedQueueTest {
  public static void main(String[] args) {
    DelayQueue queue = new DelayQueue();

  }


}
