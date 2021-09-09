package bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

public class HdfsTest {
  String url = "hdfs://hadoop1:8020";
  Configuration cfg;
  FileSystem fileSystem;


  @BeforeEach
  public void setup() throws IOException, InterruptedException {
    cfg = new Configuration();
    cfg.set("dfs.replication", "1"); //一个副本
    fileSystem = FileSystem.get(URI.create(url), cfg, "root");
  }

  @Test
  public void mkdir() throws IOException {
    var path = new Path("/javaApi");
    if (!fileSystem.exists(path)) {
      fileSystem.mkdirs(path);
      System.out.println("directory '/javaApi' is created.");
    }
  }

  @Test
  public void upload() throws IOException {
    var source = new Path("settings.gradle");
    var dest = new Path("/javaApi/settings.gradle");

    //上传并删除本地文件
//    fileSystem.copyFromLocalFile(true, source,dest);
    fileSystem.copyFromLocalFile(source, dest);
  }

  @Test
  public void download() throws IOException {
    var src = new Path("/javaApi/settings.gradle");
    var dest = new Path("test");

    //下载并删除hdfs上的文件
//    fileSystem.copyToLocalFile(true, src, dest);
    fileSystem.copyToLocalFile(src, dest);
  }

  /**
   * 查看HDFS文件的内容
   *
   * @throws Exception
   */
  @Test
  public void cat() throws Exception {
    FSDataInputStream inputStream = fileSystem.open(new Path("/javaApi/settings.gradle"));
    IOUtils.copyBytes(inputStream, System.out, 1024);
    inputStream.close();

  }

  @Test
  public void listStatus() throws IOException {
    FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/javaApi/"));
    for (var fileStatus : fileStatuses) {
      System.out.println("file=" + fileStatus.getPath().toString());
      System.out.println("is file=" + fileStatus.isFile());
      System.out.println("replication=" + fileStatus.getReplication());
    }
  }

  /**
   * 上传大文件且显示进度
   */
  @Test
  public void copyFromLocalFileWithProgress() throws Exception {
    var in = new BufferedInputStream(new FileInputStream(new File("/home/jujucom/Desktop/openjdk-11+28_linux-x64_bin.tar.gz")));
    // 带进度提醒
    FSDataOutputStream outputStream = fileSystem.create(new Path("/javaApi/openjdk-11+28_linux-x64_bin.tar.gz"),
        () -> System.out.print("."));
    IOUtils.copyBytes(in, outputStream, 4096);
  }

  @Test
  public void delete() throws IOException {

    //循环删除
    fileSystem.delete(new Path("/javaApi/"), true);
  }

  @Test
  public void createFile() {
    var builder = fileSystem.createFile(new Path("/javaApi/hello.txt"));
    var stream = builder.overwrite(true)
        .replication((short) 1)
        .create();
  }
}
