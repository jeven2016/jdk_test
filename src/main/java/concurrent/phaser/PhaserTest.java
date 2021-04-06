package concurrent.phaser;

import java.util.concurrent.Phaser;

/**
 * todo need further study
 */
public class PhaserTest {
  public static void main(String[] args) {

    Phaser phaser = new Phaser();
    phaser.bulkRegister(3);

    Thread thr1 = new Thread(() -> {
//      phaser.arrive()
      for (int i = 1; i < 4; i++) {
        if (i % 2 == 0) {
           phaser.arriveAndDeregister();
        }
      }

    });

    Thread thr2 = new Thread(() -> {

    });

    Thread thr3 = new Thread(() -> {

    });

  }
}
