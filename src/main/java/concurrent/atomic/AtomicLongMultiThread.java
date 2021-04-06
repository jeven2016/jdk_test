package concurrent.atomic;


//import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongMultiThread {

  AtomicLong val = new AtomicLong(1);

//  @Test
  public void addDel() {
    ScheduledExecutorService scheduledExecutorService = Executors
            .newScheduledThreadPool(2);

    Runnable addTask = new Runnable() {
      int times;

      @Override
      public void run() {
         if(times >=10){
           return;
         }
        System.out.println(Thread.currentThread().getName()+"->"+val.addAndGet
                (1));
      }
    };

//    scheduledExecutorService.scheduleAtFixedRate()
  }

}
