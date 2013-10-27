import java.io.*;
import java.net.*;

abstract class TcpClient extends TcpObject {
  private static String host = "localhost";

  public void start() {
    start(port);
  }

  public void start(Integer serverPort) {
    try {
      Socket socket = new Socket(host, serverPort);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      String inputLine, outputLine;
      Protocol protocol = getProtocol();

      // initiate conversation
      out.println(protocol.getMessage());
      while ((inputLine = in.readLine()) != null) {
        outputLine = protocol.processInput(inputLine);
        if (protocol.disconnect()) {
          break; 
        }
        out.println(outputLine);
      }

      out.close();
      in.close();
      socket.close();
    }
    catch(Exception e) {
      Util.printException("TcpClient - start", e);
    }
  }
}
