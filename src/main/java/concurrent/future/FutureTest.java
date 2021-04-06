package concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {

  public static void main(String[] args) {
    Callable<String> callable = () -> "callable";
    FutureTask<String> task = new FutureTask<>(callable);
    task.run();
    try {
      String value = task.get();
      System.out.println("value is " + value);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }


  }

}
