package nio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class URLTest {

  public static void main(String[] args) throws InterruptedException {
    simpletest("http://www.socs.uts.edu.au:80/MosaicDocs-old/url-primer.html");
    TimeUnit.SECONDS.sleep(2);
    simpletest("registry://192.168.1.7:9090/com.alibaba.service1?param1=value1&param2=value2");
  }

  public static void simpletest(String link) {
    try {
      System.out.println("=============================");
      URL url = new URL(link);
      System.out.println("protocol=" + url.getProtocol());
      System.out.println("protocol=" + url.getHost());
      System.out.println("protocol=" + url.getPort());
      System.out.println("protocol=" + url.getPath());

      System.out.println("getRef=" + url.getRef());
      System.out.println("getAuthority=" + url.getAuthority());
//      System.out.println("getContent=" + url.getContent().toString());
      System.out.println("getUserInfo=" + url.getUserInfo());
      System.out.println("getFile=" + url.getFile());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

}
