import java.io.*;

class Util {
  public static void printException(String methodName, Exception e) {
    System.out.println(methodName);
    System.out.println(e.getMessage());
    System.out.println(e.getClass());
  }

  public static String toHexString(byte[] _bytes) {
    return toHexString(_bytes, true);
  }

  public static String toHexString(byte[] _bytes, boolean prependX) {
    String hex = "";

    if(prependX) {
      hex = "0x";
    }

    for(Byte b : _bytes) {
      hex = hex + String.format("%02X", b & 0xFF);
    }

    return hex;
  }

  public static void write_to_file(String filename, String content) {
    try {

      File file = new File(filename);
      if (!file.exists()) {
        file.createNewFile();
      }
 
      FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(content + "\n");
      bw.close();
 
      System.out.println(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
