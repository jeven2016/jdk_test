package others;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class SecurityUti {

  public static final String KEY_SHA = "SHA";
  public static final String KEY_MD5 = "MD5";

  /**
   * MAC算法可选以下多种算法
   * <p>
   * <pre>
   * HmacMD5
   * HmacSHA1
   * HmacSHA256
   * HmacSHA384
   * HmacSHA512
   * </pre>
   */
  public static final String KEY_MAC = "HmacMD5";

  /**
   * BASE64解密
   */
  public static byte[] decryptBASE64(String key) throws Exception {
    Decoder decoder = Base64.getDecoder();
    return decoder.decode(key);
  }

  /**
   * BASE64加密
   */
  public static String encryptBASE64(byte[] key) throws Exception {
    Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(key);
  }

  /**
   * MD5加密
   */
  public static byte[] encryptMD5(byte[] data) {

    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance(KEY_MD5);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("faild to get digest instance of MD5");
    }
    md5.update(data);

    return md5.digest();

  }

  /**
   * SHA加密
   */
  public static byte[] encryptSHA(byte[] data) throws Exception {

    MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
    sha.update(data);

    return sha.digest();

  }

  /**
   * 初始化HMAC密钥
   */
  public static String initMacKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

    SecretKey secretKey = keyGenerator.generateKey();
    return encryptBASE64(secretKey.getEncoded());
  }

  /**
   * HMAC加密
   */
  public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

    SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    mac.init(secretKey);

    return mac.doFinal(data);

  }
}
