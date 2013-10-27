public class Bob extends TcpServer {
  public static Integer PORT = 8889;

  public Protocol getProtocol() {
    return new NeedhamSchroederServerProtocol();
  }

  public Integer getPort() {
    return PORT;
  }
}

