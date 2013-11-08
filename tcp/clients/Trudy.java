package tcp.clients;
import java.io.*;
import java.net.*;

import protocols.*;
import tcp.servers.*;

public class Trudy extends TcpClient {
  public static Integer PORT = 8869;
  private Protocol protocol;

  public Trudy() {
    protocol = new NeedhamSchroederReflectionProtocol();
  }

  public Integer getPort() {
    return PORT;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void start() {
    start(Bob.PORT);
  }
}

