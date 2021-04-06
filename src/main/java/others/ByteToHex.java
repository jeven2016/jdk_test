package others;


public class ByteToHex {
  public static void main(String[] args) {
    ipToInt();
  }

  public static void ipToInt() {
    String ip ="166.244.33.22";
    String[] subip= ip.split("\\.");

    int ipa = Integer.parseInt(subip[0]);
    System.out.println(Integer.toHexString(ipa));
    int ipb = Integer.parseInt(subip[1]);
    int ipc = Integer.parseInt(subip[2]);
    int ipd = Integer.parseInt(subip[3]);

    int val = ipa<<24 & ipb<<16 & ipc<<8 &ipd;

    System.out.println("val="+val);
  }
}
