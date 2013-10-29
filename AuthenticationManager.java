import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.xml.bind.DatatypeConverter;
import java.nio.*;
import java.util.Arrays;

class AuthenticationManager {
  private static String format = "DESede/ECB/NOPADDING";
  private static String format1 = "DESede/ECB/PKCS5Padding";
  private static String encryptionAlgorithm = "DESede";
  private Cipher cipher = null;
  private SecretKey key = null;
  private SecureRandom rand = null;
  private static int BIT_ALIGNMENT = 8;

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

  public byte[] encrypt(String message) {
    byte[] cipherText = null;

    try {
      //byte[] bytes = padMessage(message.getBytes("UTF-8"));
      byte[] bytes = message.getBytes("UTF-8");
      cipherText = getCipher(Cipher.ENCRYPT_MODE).doFinal(bytes);
    }
    catch(Exception e) {
      Util.printException("encrypt", e);
    }

    return cipherText;
  }

  public Cipher getCipher(int mode) {
    try {
      if(cipher == null) {
        cipher = Cipher.getInstance(format1);
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

  public byte[] padMessage(byte[] bytes) {
    return padMessage(bytes, BIT_ALIGNMENT);
  }

  public byte[] padMessage(byte[] bytes, int bit_alignment) {
    int paddingSize = bit_alignment - (bytes.length % bit_alignment);
    int newSize = bytes.length + paddingSize;
    byte[] paddedBytes = Arrays.copyOf(bytes, newSize);
System.out.println(newSize);
System.out.println(bytes.length + " + " + paddingSize + " = " + newSize);
    return paddedBytes;
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

  public long[] extractNonces(String cipherText) {
    long[] nonces = new long[2];
    byte[] bytes = Util.toByteArray(cipherText);
    int padding = bytes[bytes.length - 1];
    System.out.println("Cipher: " + cipherText);
    System.out.println("Padding: " + padding);
    System.out.println("Size: " + bytes.length);

    return nonces;
  }

  public static void main(String[] args) throws Exception {
    String keyStr1 = "DEADBEEFDEADBEEFDEADBEEF";
    String keyStr2 = "DEADBEEFDEADBEEFDEADBEEF";
    AuthenticationManager authman1 = new AuthenticationManager(keyStr1); 
    long nonce1 = authman1.getNonce();
    long nonce2 = authman1.getNonce();
    System.out.println("N1: " + nonce1);
    System.out.println("N2: " + nonce1);
    String n0 = ",";
    String n1 = String.valueOf(nonce1);
    String n2 = String.valueOf(nonce2);
    String n3 = nonce1 + "," + nonce2;

    String test0 = Util.toHexString(authman1.encrypt(n0));
    String test1 = Util.toHexString(authman1.encrypt(n1));
    String test2 = Util.toHexString(authman1.encrypt(n2));
    String test3 = Util.toHexString(authman1.encrypt(n3));
    authman1.extractNonces(test0);
    authman1.extractNonces(test1);
    authman1.extractNonces(test2);
    authman1.extractNonces(test3);

    System.out.println(authman1.decrypt(Util.toByteArray(test0)));
    System.out.println(authman1.decrypt(Util.toByteArray(test1)));
    System.out.println(authman1.decrypt(Util.toByteArray(test2)));
    System.out.println(authman1.decrypt(Util.toByteArray(test3)));
  }
}

