import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.xml.bind.DatatypeConverter;
import java.nio.*;

class AuthenticationManager {
  private static String format = "DESede/CBC/PKCS5Padding";
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

  public byte[] decryptBytes(byte[] cipher) {
    byte[] messageBytes = null;

    try {
      messageBytes = getCipher(Cipher.DECRYPT_MODE).doFinal(cipher);
    }
    catch(Exception e) {
      Util.printException("decrypt", e);
    }

    return messageBytes;
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

  public byte[] encrypt(byte[] message) {
    byte[] cipherText = null;

    try {
      cipherText = getCipher(Cipher.ENCRYPT_MODE).doFinal(message);
    }
    catch(Exception e) {
      Util.printException("encrypt", e);
    }

    return cipherText;
  }

  public byte[] encrypt(String message) {
    byte[] cipherText = null;

    try {
      cipherText = getCipher(Cipher.ENCRYPT_MODE).doFinal(message.getBytes("UTF-8"));
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
    SecretKey newKey = null;
    DESedeKeySpec spec;
    SecretKeyFactory factory;

    try {
      byte[] keyInBytes = strKey.getBytes("UTF-8");
      spec = new DESedeKeySpec(keyInBytes);
      factory = SecretKeyFactory.getInstance(encryptionAlgorithm);
      newKey = factory.generateSecret(spec);
    }
    catch(Exception e) {
      Util.printException("stringToKey", e);
    }

    return newKey;
  }


//  public static void main(String[] args) throws Exception {
//    String keyStr1 = "DEADBEEFDEADBEEFDEADBEEF";
//    String keyStr2 = "DEADBEEFDEADBEEFDEADBEEF";
//    AuthenticationManager authman1 = new AuthenticationManager(keyStr1); 
//    AuthenticationManager authman2 = new AuthenticationManager(keyStr2); 
//    String big = keyStr1 + keyStr1 + keyStr1 + keyStr1 + keyStr1 + keyStr2;
//    String test1 = Util.toHexString(authman1.encrypt(big));
//    String test2 = authman2.decrypt(Util.toByteArray(test1));
//    System.out.println(test2);
//  }
}

