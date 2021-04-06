package visitor;

/**
 * Created by root on 15-3-12.
 */
public class PersonTest {
  public static void main(String[] args) {
    calculateSalary();
  }

  public static void calculateSalary() {
    IWorker wangWorker = new SimpleWorker("wang", 24, 8900);
    IWorker liuWorker = new HighLevelWorker("liu", 32, 15000);

    Istatistics simpleHandler = new SimpleWorkerHandler();
    Istatistics higherHandler = new HighLevelWorkerHandler();

    wangWorker.calculateSalary(simpleHandler);
    liuWorker.calculateSalary(higherHandler);
  }
}
