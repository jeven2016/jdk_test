package visitor;


public class SimpleWorkerHandler implements Istatistics {
  @Override
  public void calculateSalary(IWorker worker) {
    System.out.println(String.format("Simple worker %s's salary is %d", worker
                    .getName(),
            worker.getSalary()));
  }

  @Override
  public void collectAge(IWorker worker) {
    System.out.println(String.format("Simple worker %s's age is %d", worker.getName(),
            worker.getAge()));
  }
}
