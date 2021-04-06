package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.*;

public class ChannelToByteBufferTest {
  public static void main(String[] args) {

  }

  /**
   * get byte channel
   *
   * @param filePath
   * @return
   */
  static SeekableByteChannel getChannel(String filePath) {
    Path path = Paths.get(filePath);
    SeekableByteChannel channel = null;
    try {
      channel = Files.newByteChannel(path, EnumSet.of
              (READ,
                      APPEND, WRITE));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return channel;
  }

  static FileChannel getFileChannel(String path) {
    Path filePath = Paths.get(path);
    FileChannel channel = null;
    try {
      channel = FileChannel.open(filePath,
              READ,
              APPEND, WRITE,
              TRUNCATE_EXISTING);

      channel.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return channel;
  }

  static void testScatteringReads() {
    ByteBuffer buffer1 = ByteBuffer.allocate(3);
    ByteBuffer buffer2 = ByteBuffer.allocate(3);

    FileChannel channel = getFileChannel("");
    try {
      channel.read(new ByteBuffer[]{
              buffer1, buffer2
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
