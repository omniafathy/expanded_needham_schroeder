import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.xml.bind.DatatypeConverter;
import java.nio.*;

class AuthenticationManager {
  private static String format = "DESede/CBC/NOPADDING";
  private static String encryptionAlgorithm = "DESede";
  private Cipher cipher = null;
  private SecretKey key = null;
  private SecureRandom rand = null;

  public AuthenticationManager(String _key) {
    try {
      key = stringToKey(_key);
      rand = new SecureRandom();
    }
    catch(Exception e) {
      Util.printException("Constructor", e);
    }
  }

  public String decrypt(byte[] cipher) {
    byte[] messageBytes = null;
    String message = null;

    try {
      messageBytes = getCipher(Cipher.DECRYPT_MODE).doFinal(cipher);
      message = new String(messageBytes);
    }
    catch(Exception e) {
      Util.printException("decrypt", e);
    }

    return message;
  }

  public byte[] encrypt(String message) {
    byte[] cipherText = null;

    try {
      cipherText = getCipher(Cipher.ENCRYPT_MODE).doFinal(message.getBytes());
    }
    catch(Exception e) {
      Util.printException("encrypt", e);
    }

    return cipherText;
  }

  public Cipher getCipher(int mode) {
    try {
      if(cipher == null) {
        cipher = Cipher.getInstance(encryptionAlgorithm);
      }

      cipher.init(mode, key);
    } catch(Exception e) {
      Util.printException("getCipher", e);
    }

    return cipher;
  }

  public long getNonce() {
    byte[] nonce = new byte[64];
    rand.nextBytes(nonce);
    return ByteBuffer.wrap(nonce).getLong();
  }

  public void printSecretKey() {
    System.out.println(Util.toHexString(key.getEncoded()));
  }

  // accepts a key as a string and returns a key
  public SecretKey stringToKey(String strKey) {
    byte[] keyInBytes = strKey.getBytes();
    SecretKey newKey = null;
    DESedeKeySpec spec;
    SecretKeyFactory factory;

    try {
      spec = new DESedeKeySpec(keyInBytes);
      factory = SecretKeyFactory.getInstance(encryptionAlgorithm);
      newKey = factory.generateSecret(spec);
    }
    catch(Exception e) {
      Util.printException("stringToKey", e);
    }

    return newKey;
  }


 // public static void main(String[] args) {
 //   String keyStr = "DEADBEEFDEADBEEFDEADBEEF";
 //   AuthenticationManager authman = new AuthenticationManager(keyStr); 
 //   System.out.println(authman.encrypt("foobarss"));
 //   for(int i = 0; i < 10; i++) {
 //     long nonce = authman.getNonce();
 //     String ticket = keyStr + "Alice" + nonce;
 //     System.out.println(authman.encrypt(Long.toString(nonce)));
 //     System.out.println(Long.toString(nonce));
 //   }
 // }
}

