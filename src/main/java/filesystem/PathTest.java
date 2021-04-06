package filesystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by root on 4/27/14.
 */
public class PathTest {
  public static void main(String[] args) {
    Path path = Paths.get("/dd/kk/mm/jj");
    printPathInfo(path);

    path = Paths.get("c:/dd/kk/mm/jj");
    printPathInfo(path);
  }

  private static void printPathInfo(Path path) {
    System.out.println("---------------Path: " + path.toString() + "-------------");
    System.out.println("isRoot: " + path.isAbsolute());

    System.out.println("nameCount: " + path.getNameCount());

    System.out.println("fileName: " + path.getFileName());

    System.out.println("name(0): " + path.getName(0));

    System.out.println("getName(1).toString: " + path.getName(1).toString());

    System.out.println("getParent: " + path.getParent().toString());

    System.out.println("getRoot: " + path.getRoot());

    System.out.println("subPath: " + path.subpath(1, 3));

    Path pathUnomerize = Paths.get("/kk/mm/../../kk/./rs");
    System.out.println("normalize: " + pathUnomerize.normalize());

    try {
      System.out.println("toRealPath: " + pathUnomerize.toRealPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
