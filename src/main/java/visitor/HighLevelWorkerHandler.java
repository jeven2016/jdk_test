package visitor;

/**
 * Created by root on 15-3-12.
 */
public class HighLevelWorkerHandler implements Istatistics {
  @Override
  public void calculateSalary(IWorker worker) {
    System.out.println(String.format("High level worker %s's salary is %d",
            worker.getName(),
            worker.getSalary()));
  }

  @Override
  public void collectAge(IWorker worker) {
    System.out.println(String.format("High level worker %s's salary is %d", worker.getName(),
            worker.getSalary()));
  }
}
