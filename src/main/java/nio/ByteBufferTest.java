package nio;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferTest {

  public static void main(String[] args) {
    ByteBufferTest bt = new ByteBufferTest();
    bt.test1();
    bt.testCompactAndClear();
    bt.testMrakReset();
  }

  /**
   * generate a ByteBuffer
   * @param limit
   * @param cap
   * @return
   */
  public ByteBuffer generateByteBuffer(int limit, int cap) {
    ByteBuffer buffer = ByteBuffer.allocate(cap);
    buffer.limit(limit);
    return buffer;
  }

  /*
  equals()
  Two buffers are equal if:
  1. They are of the same type (byte, char, int etc.)
  2. They have the same amount of remaining bytes, chars etc. in the buffer.
  3. All remaining bytes, chars etc. are equal.*/
  public void testEquals(){
    ByteBuffer buffer = generateByteBuffer(8, 10);
    ByteBuffer buffer2 = generateByteBuffer(8, 10);
  }

  public void testMrakReset(){
    ByteBuffer buffer = generateByteBuffer(8, 10);
    printMethodInfo("method: testMrakReset");
    buffer.put((byte) 1);
    buffer.put((byte) 2);
    buffer.put((byte) 3);
    buffer.put((byte) 4);
    buffer.put((byte) 5);
    buffer.put((byte) 6);
    printInfo(buffer);

    //flip,切换至读取模式
    buffer.flip();
    buffer.getShort();
    buffer.getShort(1);  //此调用不会increase position
    System.out.println("after flip and read a short: ");
    printInfo(buffer);

    //标记当前position
    buffer.mark();
    buffer.getShort();
    buffer.getShort(2);   //此调用不会increase position
    System.out.println("after mark and read a short: ");
    printInfo(buffer);
    buffer.reset();
    System.out.println("after reset: ");
    printInfo(buffer);
  }

  public void testCompactAndClear() {
    ByteBuffer buffer = generateByteBuffer(8, 10);
    printMethodInfo("method: testCompactAndClear");
    buffer.put((byte) 1);
    buffer.put((byte) 2);
    buffer.put((byte) 3);
    buffer.put((byte) 4);
    buffer.put((byte) 5);
    buffer.put((byte) 6);
    printInfo(buffer);

    //调用flip,从写模式切换至读模式，且postion为开始位置，limit为最后一个数据的position
    buffer.flip();

    //读出一个int
    if(buffer.hasRemaining()){
      buffer.getInt();
    }
    printInfo(buffer);

    //compact,将剩余未读取的数据拷贝到从0开始的位置，并将position设置为最后一个位置+1, limit设置为最大容量
    buffer.compact();
    System.out.println("compact:");
    printInfo(buffer);

    //再写入一个byte
    buffer.put((byte)7);
    printInfo(buffer);

    //clear之后
    buffer.clear();
    printInfo(buffer);
  }


  public void test1() {
    ByteBuffer buffer = generateByteBuffer(18, 20);
    printMethodInfo("method: test1");
    printInfo(buffer);

    //put 18 Bytes
    buffer.putInt(1);
    buffer.putInt(2);
    buffer.putInt(3);
    buffer.putInt(4);

    buffer.put((byte) 5);
    buffer.put((byte) 6);
    System.out.println("put 18 bytes");

    //position=limit=capacity
    printInfo(buffer);

    try {
      //超出limit限制，异常抛出
      buffer.put((byte) 1);
    } catch (Exception e) {
      System.out.println("写入1个Byte失败，超出limit限制");
    }

    //flip
    buffer.flip();
    System.out.printf("flip() 之后:");
    printInfo(buffer);

    System.out.printf("buffer.hasArray()=%b\n", buffer.hasArray());

    //获取3个Byte
    get3Integers(buffer);

    //flip： limit= position,position=0
    buffer.flip();
    System.out.printf("flip() 之后:");
    printInfo(buffer);

    //获取3个Byte
    get3Integers(buffer);

  }

  private void printMethodInfo(String msg) {
    System.out.println("==============================");
    System.out.println(msg);
  }

  private void printInfo(ByteBuffer buffer) {
    System.out.printf("position=%d, limit=%d, cap=%d\n", buffer.position(),
            buffer.limit(), buffer.capacity());
  }

  private void get3Integers(ByteBuffer buffer) {
    List<Integer> list = new ArrayList<>();
    int val;
    int i = 0;
    while (buffer.hasRemaining() && i++ < 3) {
      val = buffer.getInt();
      list.add(val);
    }

    System.out.printf(">获取3个Int值，输出为:" + list + "\n");
    System.out.printf(">获取3个Int值之后, position=%d, limit=%d, cap=%d\n",
            buffer.position(), buffer.limit(), buffer.capacity());
  }
}
