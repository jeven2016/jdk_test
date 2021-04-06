package concurrent;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 使用Thread.interrupt()中断线程
 * Thread.interrupt()方法不会中断一个正在运行的线程。这一方法实际上完成的是，
 * 在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态。更确切的说，
 * 如果线程被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞，那么，
 * 它将接收到一个中断异常（InterruptedException），从而提早地终结被阻塞状态。
 * <p/>
 * 因此，如果线程被上述几种方法阻塞，正确的停止线程方式是设置共享变量，并调用interrupt()
 * （注意变量应该先设置）。如果线程没有被阻塞，这时调用interrupt()将不起作用；否则，线程就将得到异常（该线程必须事先预备好处理此状况）
 * ，接着逃离阻塞状态。在任何一种情况中，最后线程都将检查共享变量然后再停止。
 */
public class InterruptExceptionTest {
  public static void main(String[] args) {

    FileSearch2 searcher = new FileSearch2("/opt", "comm.java");
    Thread thread = new Thread(searcher);
    thread.start();

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    thread.interrupt();
  }
}


class FileSearch implements Runnable {

  private String initPath;
  private String fileName;

  public FileSearch(String initPath, String fileName) {
    this.initPath = initPath;
    this.fileName = fileName;
  }


  @Override
  public void run() {

    File file = new File(initPath);
    if (file.isDirectory()) {
      try {
        directoryProcess(file);
      } catch (InterruptedException e) {
        System.out.printf("%s: The search has been interrupted", Thread.currentThread().getName());
      }
    }

  }


  private void directoryProcess(File file) throws
          InterruptedException {
    File list[] = file.listFiles();
    if (list != null) {
      for (int i = 0; i < list.length; i++) {
        if (list[i].isDirectory()) {
          directoryProcess(list[i]);
        } else {
          fileProcess(list[i]);
        }
      }
    }
    if (Thread.interrupted()) {
       throw new InterruptedException();
    }
  }


  private void fileProcess(File file) throws InterruptedException {
    if (file.getName().equals(fileName)) {
      System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
    }
    if (Thread.interrupted()) {
      throw new InterruptedException();
//      System.out.println("It's interrupted now.");
    }
  }


}

