package io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by root on 3/17/14.
 */
public class BufferedInputStream_test {

  public static void main(String[] args) {
    test();
  }

  static void test(){
    byte[] bytes = new byte[]{0,1,2,3,4};

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    BufferedInputStream bufferedInputStream =new BufferedInputStream(byteArrayInputStream);

    try {
      System.out.println(bufferedInputStream.read());
      bufferedInputStream.mark(1);
      System.out.println("aviable="+bufferedInputStream.available());

      System.out.println(bufferedInputStream.read());
      System.out.println(bufferedInputStream.read());
      System.out.println(bufferedInputStream.read());


      System.out.println("aviable=" + bufferedInputStream.available());
      bufferedInputStream.reset();
      System.out.println("aviable=" + bufferedInputStream.available());
      System.out.println(bufferedInputStream.read());
      bufferedInputStream.skip(2);
      System.out.println(bufferedInputStream.read());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
